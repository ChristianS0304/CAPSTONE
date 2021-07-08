package com.example.capstone;
import com.google.gson.annotations.SerializedName;


public class ContactModel {
    @SerializedName("contact")
    private String mLine1;
    //@SerializedName("username")
    //private String mUserLoggedIn;
    // Constructor
    public ContactModel(String line1) {
        mLine1 = line1;
    }

    public String getLine1() {
        return mLine1;
    }
}