package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.capstone.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, name;
    Button btnRegister;
    public static final String PREF_LOGIN = "LOGIN_PREF";
    public static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.register_btn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())
                        || TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Text Field Required", Toast.LENGTH_LONG).show();
                } else {
                    register();
                }
            }
        });
    }

    public void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setName(name.getText().toString());
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().RegisterUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {

                    RegisterResponse registerResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
                            editor.putString(KEY_CREDENTIALS, "DUMMY CREDENTIALS");
                            editor.commit();
                            Toast.makeText(RegisterActivity.this, "Welcome " + registerResponse.getUsername() + "\n", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, HomescreenActivity.class));
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            i.putExtra("data", registerResponse.getUsername());

                            finish();
                        }
                    }, 700);
                } else {
                    Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Throwable" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}