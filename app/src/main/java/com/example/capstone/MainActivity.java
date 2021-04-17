package com.example.capstone;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
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
    //TextView username;
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



        // ---------------- retrofit implementation ------------------------


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        /*
        TODO: POST username logged in and match up with stored username in database and pull users that are tied to the username logged in
        */

        JsonApiContacts jsonPlaceHolderApi = retrofit.create(JsonApiContacts.class);
        Call<ArrayList<ContactModel>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<ArrayList<ContactModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ContactModel>> call, Response<ArrayList<ContactModel>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    //return;
                }
                ArrayList<ContactModel> name = response.body();
                for (ContactModel post : name) {
                    String content = "";
                    content += post.getLine1() + "\n";
                    insertItem(content);

                    /* Intent intent = getIntent();
                    if (intent.getExtras() != null) {
                        String passedUserName = intent.getStringExtra("data");
                        if (passedUserName == content) {
                            contactsList.remove((content));
                        }
                    }*/
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ContactModel>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });

        // ---------------- retrofit implementation ------------------------

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
                insertItem(line1.getText().toString());
            }
        });
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_contacts);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new ExampleAdapter(contactsList);
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
}