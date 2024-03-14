package com.example.insightlogfe.apiService;

import com.example.insightlogfe.payload.UserDto;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/api/v1/user/uploadUserImage/{usrId}")
    Call<UserDto> uploadUserProfileImage(@Path(value = "userId") Integer userId, @Part MultipartBody.Part file) throws IOException;

    @GET("/api/v1/user/getImage/{usrId}")
    Call<?> downloadUserProfileImage(@Path(value = "userId") Integer userId);
}
