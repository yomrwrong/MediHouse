package com.example.medihouse.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.medihouse.BaseActivity;
import com.example.medihouse.HomeActivity;
import com.example.medihouse.MessageDialog;
import com.example.medihouse.OtpDialog;
import com.example.medihouse.R;
import com.example.medihouse.SharePrefs;
import com.example.medihouse.databinding.ActivityLoginBinding;
import com.example.medihouse.db.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private List<User> allUser;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);

        viewModel.getAllUsers().observe(this, users -> {
            allUser = new ArrayList<>();
            allUser.addAll(users);
        });

        binding.editTextPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.textViewError.setVisibility(View.GONE);
                if (charSequence.length() == 10) {
                    binding.textViewLogin.setTextColor(getResources().getColor(R.color.teal_200));
                    binding.textViewLogin.setBackground(getResources().getDrawable(R.drawable.btn_login_ready));
                } else {
                    binding.textViewLogin.setTextColor(getResources().getColor(R.color.color_light));
                    binding.textViewLogin.setBackground(getResources().getDrawable(R.drawable.btn_login));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.textViewError.setVisibility(View.GONE);

                if (TextUtils.isEmpty(binding.editTextPhoneNo.getText().toString())) {
                    binding.textViewError.setText("Error : Please enter your mobile no");
                    binding.textViewError.setVisibility(View.VISIBLE);
                } else if (binding.editTextPhoneNo.getText().length() != 10) {
                    binding.textViewError.setText("Error : Enter a valid mobile no");
                    binding.textViewError.setVisibility(View.VISIBLE);
                } else {

                    boolean isPresent = false;

                    for (int i = 0; i < allUser.size(); i++) {
                        if (binding.editTextPhoneNo.getText().toString().equalsIgnoreCase(allUser.get(i).getPhoneNumber())) {
                            isPresent = true;
                        }
                    }

                    if (isPresent) {
                        OtpDialog dialog = new OtpDialog();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString(OtpDialog.EXTRA_DIALOG_PHONE_NUMBER, binding.editTextPhoneNo.getText().toString());
                        dialog.setArguments(bundle);
                        dialog.show(ft, "dialog");
                        dialog.setDialogCloseListener(new OtpDialog.OtpDialogListener() {
                            @Override
                            public void closeDialog() {
                                dialog.dismiss();
                            }

                            @Override
                            public void isVerified() {
                                dialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra(HomeActivity.EXTRA_MOBILE_NUMBER, binding.editTextPhoneNo.getText().toString());
                                SharePrefs.setBooleanPref(LoginActivity.this, "isLoggedIn", true);
                                SharePrefs.setStringPref(LoginActivity.this, "userName", binding.editTextPhoneNo.getText().toString());
                                startActivity(intent);
                                finishAffinity();
                            }
                        });
                    } else {
                        showDialog("Account Not Registered", "No user account with this mobile no has been registered! Please register yourself with us by clicking the Register Now button below, If you are already registered and still unable to login, Please contact our admin team, Thank you.");
                    }
                }
            }
        });


        binding.textViewNoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SIgnUpActivity.class));
            }
        });
    }
}