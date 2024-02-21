package com.example.insightlogfe.apiService;


import com.example.insightlogfe.payload.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/v1/auth/register")
    Call<UserDto> RegisterNewUser(@Body UserDto userDto);
}
