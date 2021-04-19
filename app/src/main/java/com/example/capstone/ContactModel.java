package com.example.capstone;
import com.google.gson.annotations.SerializedName;


public class ContactModel {
    @SerializedName("username")
    private String mLine1;

    // Constructor
    public ContactModel(String line1) {
        mLine1 = line1;
    }

    public String getLine1() {
        return mLine1;
    }
}




