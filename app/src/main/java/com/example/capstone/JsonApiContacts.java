package com.example.capstone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface JsonApiContacts {
    @POST("users/")
    Call<ArrayList<ContactModel>> getUsers(ArrayList<ContactModel> ContactModel);
}

