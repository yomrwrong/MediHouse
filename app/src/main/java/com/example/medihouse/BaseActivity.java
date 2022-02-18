package com.example.medihouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.medihouse.login.SIgnUpActivity;

public class BaseActivity extends AppCompatActivity {

    public void showDialog(String title, String message){
        MessageDialog dialog = new MessageDialog();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(MessageDialog.EXTRA_DIALOG_TITLE, title);
        bundle.putString(MessageDialog.EXTRA_DIALOG_MESSAGE, message);
        dialog.setArguments(bundle);
        dialog.show(ft, "dialog");
        dialog.setDialogCloseListener(dialog::dismiss);
    }

}
