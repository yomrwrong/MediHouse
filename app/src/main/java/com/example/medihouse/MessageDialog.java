package com.example.medihouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {

    public static final String EXTRA_DIALOG_TITLE = "com.example.medihouse.title";
    public static final String EXTRA_DIALOG_MESSAGE = "com.example.medihouse.message";
    private ImageView imageViewClose;
    private MessageDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = "", message = "";

        if (getArguments() != null) {
            title = getArguments().getString(EXTRA_DIALOG_TITLE);
            message = getArguments().getString(EXTRA_DIALOG_MESSAGE);
        }

        TextView textViewTitle = view.findViewById(R.id.text_view_title);
        TextView textViewMessage = view.findViewById(R.id.text_view_message);
        TextView textViewOkay = view.findViewById(R.id.text_view_okay);
        imageViewClose = view.findViewById(R.id.image_view_close);

        textViewTitle.setText(title);
        textViewMessage.setText(message);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.closeDialog();
                }
            }
        });

        textViewOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewClose.performClick();
            }
        });
    }

    public interface MessageDialogListener{

        void closeDialog();

    }

    public void setDialogCloseListener(MessageDialogListener listener) {
        this.listener = listener;
    }
}
