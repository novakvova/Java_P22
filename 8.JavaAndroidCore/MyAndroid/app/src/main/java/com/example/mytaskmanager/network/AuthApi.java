package com.example.mytaskmanager.network;

import com.example.mytaskmanager.dto.auth.AuthResponse;
import com.example.mytaskmanager.dto.auth.LoginRequestDTO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthApi {
    @Multipart
    @POST("/auth/register")
    Call<AuthResponse> register(
            @Part("FirstName") RequestBody firstName,
            @Part("LastName") RequestBody lastName,
            @Part("Email") RequestBody email,
            @Part("Password") RequestBody password,
            @Part MultipartBody.Part image
    );

    @POST("/auth/login")
    Call<AuthResponse> login(@Body LoginRequestDTO model);
}
