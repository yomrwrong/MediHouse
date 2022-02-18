package com.example.medihouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.medihouse.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageViewLogo = findViewById(R.id.image_view_logo);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);
        imageViewLogo.startAnimation(animFadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (SharePrefs.getBooleanPref(SplashActivity.this, "isLoggedIn")){
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.putExtra(HomeActivity.EXTRA_MOBILE_NUMBER, SharePrefs.getStringPref(SplashActivity.this,"userName"));
                    startActivity(intent);
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finishAffinity();

            }
        }, 2000);

    }
}