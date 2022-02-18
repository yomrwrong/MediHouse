package com.example.medihouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chaos.view.PinView;

public class OtpDialog extends DialogFragment {

    public static final String EXTRA_DIALOG_PHONE_NUMBER = "com.example.medihouse.title";
    private ImageView imageViewClose;
    private OtpDialogListener listener;
    PinView pinView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String message = "";

        if (getArguments() != null) {
            message = getArguments().getString(EXTRA_DIALOG_PHONE_NUMBER);
        }

        TextView textViewMessage = view.findViewById(R.id.text_view_message);
        TextView textViewOkay = view.findViewById(R.id.text_view_okay);
        TextView textViewHint = view.findViewById(R.id.text_view_hint);
        imageViewClose = view.findViewById(R.id.image_view_close);

        textViewMessage.setText("Please Enter 4 digit Otp sent to you on +91 " + message);

        pinView = view.findViewById(R.id.pinView);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.closeDialog();
                }
            }
        });

        textViewOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(pinView.getText())) {
                    textViewHint.setText("In-valid OTP, Please Note Otp is 1234");
                    textViewHint.setTextColor(getResources().getColor(R.color.red));
                } else {
                    String code = pinView.getText().toString();
                    if (code.equalsIgnoreCase("1234")) {
                        if (listener != null){
                            listener.isVerified();
                        }
                    } else {
                        textViewHint.setText("In-valid OTP, Please Note Otp is 1234");
                        textViewHint.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });
    }

    public interface OtpDialogListener {

        void closeDialog();

        void isVerified();

    }

    public void setDialogCloseListener(OtpDialogListener listener) {
        this.listener = listener;
    }
}
