package com.example.capstone;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    String username;
    EditText otherUser;
    EditText nickName;
    Button btnLogin;
    EditText roomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        String passedUserName = intent.getStringExtra("data");
        //Setting a dynamic title at runtime. Here, it displays the current time.
        //actionBar.setTitle(passedUserName);
        username = passedUserName;
        nickName = findViewById(R.id.userName);
        otherUser = findViewById(R.id.roomName);
        btnLogin = findViewById(R.id.enterChat);

        /* btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsernameRequest usernameRequest = new UsernameRequest();
                usernameRequest.setOtherUser(otherUser.getText().toString());
                usernameRequest.setUsername(username);
                populateUsers(usernameRequest);
            }
        }); */


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherUser = findViewById(R.id.userName);
                roomName = findViewById(R.id.roomName);


                if(TextUtils.isEmpty(otherUser.getText().toString()) || TextUtils.isEmpty(roomName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Nickname or Chat Missing", Toast.LENGTH_LONG).show();
                } else {


                    Intent intent = new Intent(MainActivity.this, ChatRoomActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("userName", otherUser.getText().toString());
                    extras.putString("roomName", roomName.getText().toString());
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });




    }

    public void SettingsTest(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent switchToSettings = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(switchToSettings);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void populateUsers(UsernameRequest usernameRequest) {
        Call<UsernameResponse> usernameResponseCall = ApiClient.getService().getUser(usernameRequest);
        usernameResponseCall.enqueue(new Callback<UsernameResponse>() {
            @Override
            public void onResponse(Call<UsernameResponse> call, Response<UsernameResponse> response) {
                if(response.isSuccessful()) {
                    UsernameResponse usernameResponse = response.body();
                    usernameResponse.setOtherUser(otherUser.getText().toString());
                    startActivity(new Intent(MainActivity.this, ChatRoomActivity.class).putExtra("data", usernameResponse.getOtherUser()));
                }

            }

            @Override
            public void onFailure(Call<UsernameResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}