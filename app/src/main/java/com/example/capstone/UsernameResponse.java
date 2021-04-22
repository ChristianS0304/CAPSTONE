package com.example.capstone;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class UsernameResponse extends ArrayList<ContactModel> implements Serializable {

    private int user_id;
    private String username;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @NonNull
    @Override
    public Stream<ContactModel> stream() {
        return null;
    }

    @NonNull
    @Override
    public Stream<ContactModel> parallelStream() {
        return null;
    }
}
