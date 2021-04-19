package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone.LoginFinalActivity;


public class HomescreenActivity extends AppCompatActivity {

    public static final String PREF_LOGIN = "LOGIN_PREF";
    public static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        if(preferences.contains(KEY_CREDENTIALS)){//if user is currently logged in;
            finish();
        }


        Button login_Button = findViewById(R.id.login_button);
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(HomescreenActivity.this, LoginFinalActivity.class);
                startActivity(MainIntent);
                finish();
            }
        });

       Button register_Button = findViewById(R.id.register_button);
       register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(HomescreenActivity.this, RegisterActivity.class);
                startActivity(MainIntent);
            }
        });
    }
}