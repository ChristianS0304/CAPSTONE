package com.example.capstone;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
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


public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnContactListener {
    String username;
    private AppBarConfiguration mAppBarConfiguration;
    ArrayList<ContactModel> contactsList = new ArrayList<>(1);
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        String passedUserName = intent.getStringExtra("data");
        //Setting a dynamic title at runtime. Here, it displays the current time.
        actionBar.setTitle(passedUserName);
        username = passedUserName;



        // ---------------- retrofit implementation START ------------------------

        //TODO: POST username logged in and match up with stored username in database and pull users that are tied to the username logged in



        UsernameRequest usernameRequest = new UsernameRequest();
        usernameRequest.setUsername(username);
        populateUsers(usernameRequest);

        //testingArray(usernameRequest);




        // ---------------- retrofit implementation END ------------------------





        loadData();
        buildRecyclerView();
        setInsertButton();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToChat = new Intent(MainActivity.this, SimpleChat.class);
                MainActivity.this.startActivity(switchToChat);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loadData() {

        if (contactsList == null) {
            contactsList = new ArrayList<>();
        }
    }

    private void setInsertButton() {
        Button buttonInsert = findViewById(R.id.addContact);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line1 = findViewById(R.id.edittext_line_1);
                //insertItem(line1.getText().toString());
            }
        });
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_contacts);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new ExampleAdapter(contactsList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void insertItem(String line1) {
        contactsList.add(new ContactModel(line1));
        mAdapter.notifyItemInserted(contactsList.size());
    }

    public void openSimpleChat(View view) {
        Intent switchToChat = new Intent(MainActivity.this, SimpleChat.class);
        MainActivity.this.startActivity(switchToChat);
    }

    @Override
    public void onContactClick(int position) {
        Intent intent = new Intent(this, SimpleChat.class);
        intent.putExtra("usernameChat", String.valueOf(contactsList.get(position).getLine1()));
        startActivity(intent);
    }


    public void populateUsers(UsernameRequest usernameRequest) {
        Call<UsernameResponse> usernameResponseCall = ApiClient.getService().getUser(usernameRequest);
        usernameResponseCall.enqueue(new Callback<UsernameResponse>() {
            @Override
            public void onResponse(Call<UsernameResponse> call, Response<UsernameResponse> response) {
                if(response.isSuccessful()) {

                    ArrayList<ContactModel> userAdded = response.body();

                    for (ContactModel post : userAdded) {
                        String content = "";
                        content += post.getLine1() + "\n";
                        //String usernameContact = "";
                        //usernameContact += post.getUsernameContact() + "\n";
                        insertItem(content);
                    }


                }

            }

            @Override
            public void onFailure(Call<UsernameResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }


}