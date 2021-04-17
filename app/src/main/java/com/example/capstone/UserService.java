package com.example.capstone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("authenticate/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginrequest);

    @POST("users/")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
