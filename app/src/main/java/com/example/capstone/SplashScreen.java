package com.example.capstone;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.HomescreenActivity;

public class SplashScreen extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background_splash);

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        if(preferences.contains(KEY_CREDENTIALS)){//if user is currently logged in;
            Intent i = new Intent (SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();


        }else {//if user is not yet logged in;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent (SplashScreen.this, HomescreenActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
}
