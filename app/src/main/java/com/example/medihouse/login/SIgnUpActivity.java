package com.example.medihouse.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.example.medihouse.BaseActivity;
import com.example.medihouse.HomeActivity;
import com.example.medihouse.MessageDialog;
import com.example.medihouse.OtpDialog;
import com.example.medihouse.R;
import com.example.medihouse.SharePrefs;
import com.example.medihouse.databinding.ActivitySignUpBinding;
import com.example.medihouse.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SIgnUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;
    private LoginViewModel viewModel;
    private List<User> allUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);

        viewModel.getAllUsers().observe(this, users -> {
            allUser = new ArrayList<>();
            allUser.addAll(users);
        });

        binding.textViewContinue.setOnClickListener(view -> {

            binding.textViewFirstNameError.setVisibility(View.GONE);
            binding.textViewLastNameError.setVisibility(View.GONE);
            binding.textViewPhoneNoError.setVisibility(View.GONE);
            binding.textViewEmailError.setVisibility(View.GONE);

            if (TextUtils.isEmpty(binding.editTextFirstName.getText().toString())) {
                //Error
                binding.textViewFirstNameError.setText("Error : First Name is required");
                binding.textViewFirstNameError.setVisibility(View.VISIBLE);
            } else {

                if (TextUtils.isEmpty(binding.editTextLastName.getText().toString())) {
                    //Error
                    binding.textViewLastNameError.setText("Error : Last Name is required");
                    binding.textViewLastNameError.setVisibility(View.VISIBLE);
                } else {

                    if (TextUtils.isEmpty(binding.editTextPhoneNo.getText().toString())) {
                        //Error
                        binding.textViewPhoneNoError.setText("Error : Phone No is required");
                        binding.textViewPhoneNoError.setVisibility(View.VISIBLE);
                    } else {

                        if (binding.editTextPhoneNo.getText().length() == 10) {

                            if (TextUtils.isEmpty(binding.editTextEmail.getText().toString())) {
                                //REGISTER USER
                                registerUser("NA");
                            } else {
                                //CHECK EMAIL FORMAT
                                if (isEmailValid(binding.editTextEmail.getText().toString())) {
                                    //REGISTER USER
                                    registerUser(binding.editTextEmail.getText().toString());
                                } else {
                                    //ERROR
                                    binding.textViewEmailError.setText("Error : Please enter a valid E-mail.");
                                    binding.textViewEmailError.setVisibility(View.VISIBLE);
                                }
                            }

                        } else {
                            binding.textViewPhoneNoError.setText("Error : Please enter a valid Phone No.");
                            binding.textViewPhoneNoError.setVisibility(View.VISIBLE);
                        }
                    }

                }

            }
        });
    }

    private void registerUser(String email) {

        boolean isPresent = false;

        for (int i = 0; i < allUser.size(); i++) {

            if (binding.editTextPhoneNo.getText().toString().equalsIgnoreCase(allUser.get(i).getPhoneNumber())) {
                isPresent = true;
            }

        }

        if (isPresent) {
            showDialog("Account Already Registered", "A user account with this mobile no has already been registered! Please try to login, If you are unable to login with this number, Please contact our admin team, Thank you.");
        } else {

            OtpDialog otpdialog = new OtpDialog();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString(OtpDialog.EXTRA_DIALOG_PHONE_NUMBER, binding.editTextPhoneNo.getText().toString());
            otpdialog.setArguments(bundle);
            otpdialog.show(ft, "dialog");
            otpdialog.setDialogCloseListener(new OtpDialog.OtpDialogListener() {
                @Override
                public void closeDialog() {
                    otpdialog.dismiss();
                }

                @Override
                public void isVerified() {
                    otpdialog.dismiss();
                    User user = new User(binding.editTextFirstName.getText().toString(), binding.editTextLastName.getText().toString(), binding.editTextPhoneNo.getText().toString(), email);
                    viewModel.registerUser(user);
                    MessageDialog dialog = new MessageDialog();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString(MessageDialog.EXTRA_DIALOG_TITLE, "Account Created Successfully!");
                    bundle.putString(MessageDialog.EXTRA_DIALOG_MESSAGE, "Your Account has been created successfully, Your Default Otp is 1234 to login.");
                    dialog.setArguments(bundle);
                    dialog.show(ft, "dialog");
                    dialog.setDialogCloseListener(new MessageDialog.MessageDialogListener() {
                        @Override
                        public void closeDialog() {
                            dialog.dismiss();
                            Intent intent = new Intent(SIgnUpActivity.this, HomeActivity.class);
                            intent.putExtra(HomeActivity.EXTRA_MOBILE_NUMBER, binding.editTextPhoneNo.getText().toString());
                            SharePrefs.setBooleanPref(SIgnUpActivity.this, "isLoggedIn", true);
                            SharePrefs.setStringPref(SIgnUpActivity.this, "userName", binding.editTextPhoneNo.getText().toString());
                            startActivity(intent);
                            finishAffinity();
                        }
                    });
                }
            });
        }

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}