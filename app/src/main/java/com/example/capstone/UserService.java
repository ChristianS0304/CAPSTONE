package com.example.capstone;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginrequest);

    @POST("register/")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);


    //TODO: figure out how to call nested arrays and replace below
    @POST("users/")
    Call<UsernameResponse> getUser(@Body UsernameRequest usernameRequest);

}
