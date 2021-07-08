package com.example.capstone;

import android.widget.EditText;

public class UsernameRequest {

    private String username;
    private String otherUser;

    public String getOtherUser() {
        return otherUser;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }
}
