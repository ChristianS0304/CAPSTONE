package com.example.capstone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginrequest);

    @POST("register/")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);

    @POST("users/")
    Call<RegisterResponse> AddUser(@Body AddUserRequest addUserRequest);
}
