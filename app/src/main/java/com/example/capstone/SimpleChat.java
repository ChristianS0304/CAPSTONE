package com.example.capstone;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Activity;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstone.ui.main.SimpleChatFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SimpleChat extends AppCompatActivity {
    public static final String Xtra_Msg = "com.example.myfirstapp.MESSAGE";
    RecyclerView recyclerView;
    ArrayList<MessageModel> messagesList = new ArrayList<>(2);

    public void sendMessage(View v) {
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
        String sentMessage = textInputLayout.getEditText().getText().toString();

        for (int i = 0; i < 1; i++) {
            messagesList.add(new MessageModel(sentMessage, i % 2 == 0 ? CustomAdapter.MESSAGE_TYPE_OUT : CustomAdapter.MESSAGE_TYPE_IN));
        }

        textInputLayout.getEditText().getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_chat_activity);
        //final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SimpleChatFragment.newInstance())
                    .commitNow();
        }



        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            Intent intent = getIntent();
            String passedUserName = intent.getStringExtra("otherUser");
            //Setting a dynamic title at runtime. Here, it displays the current time.
            actionBar.setTitle(passedUserName);
        }


        CustomAdapter adapter = new CustomAdapter(this, messagesList);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /* public void sendMessage(View view) //Send button response
    {
        Intent intent = getIntent();
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        EditText editText = (EditText) findViewById(R.id.message_input);
        String message = editText.getText().toString();
        intent.putExtra(Xtra_Msg, message);
        message = intent.getStringExtra(SimpleChat.Xtra_Msg);
        TextView message_out = findViewById(R.id.message_output);
        if(message_out.hasSelection() == false)
        {
            // Capture the layout's TextView and set the string as its tex
            message_out.setText(message);
        }
        else
        {
            message_out.getLayoutParams();
            params1.addRule(RelativeLayout.ABOVE, message_out.getId());
            message_out.setLayoutParams(params1);
            TextView nwTxt = findViewById(R.id.message_output);
            nwTxt.setText(message);
        }
        //Creates a new text box
        //TextView nwTxt = new TextView(SimpleChat.this);
        //nwTxt.setId(R.id.message_output);
        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        //((FrameLayout) findViewById(R.id.simple_chat)).addView(nwTxt, params1);
        */

}