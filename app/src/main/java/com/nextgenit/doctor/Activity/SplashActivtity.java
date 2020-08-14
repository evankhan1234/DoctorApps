package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.SharedPreferenceUtil;

public class SplashActivtity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activtity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLoginPage();
            }
        }, 3000);
    }
    private void goToLoginPage() {
        if (SharedPreferenceUtil.getUserID(SplashActivtity.this)==null|| SharedPreferenceUtil.getUserID(SplashActivtity.this).equals("")) {
            Intent i = new Intent(SplashActivtity.this, StartActivity.class);
            startActivity(i);
            finish();
        } else {
            startActivity(new Intent(SplashActivtity.this, StartActivity.class));
            finish();

        }

    }
}