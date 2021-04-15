package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone.ui.login.LoginActivity;


public class HomescreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        Button login_Button = findViewById(R.id.login_button);
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(HomescreenActivity.this, LoginActivity.class);
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
                finish();
            }
        });
    }
}