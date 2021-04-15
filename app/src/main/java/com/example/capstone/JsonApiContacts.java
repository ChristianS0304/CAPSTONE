package com.example.capstone;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface JsonApiContacts {
    @GET("profile")
    Call<ArrayList<ContactModel>> getPosts();
}

