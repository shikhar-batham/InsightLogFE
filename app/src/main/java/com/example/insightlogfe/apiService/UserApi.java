package com.example.insightlogfe.apiService;

import com.example.insightlogfe.payload.UserDto;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @Multipart
    @POST("/api/v1/user/uploadUserImage/{userId}")
    Call<ResponseBody> uploadUserProfileImage(@Path("userId") Integer userId,
                                              @Part MultipartBody.Part image);

    @GET("/api/v1/user/getImage/{userId}")
    Call<ResponseBody> downloadUserProfileImage(@Path("userId") Integer userId);

    @GET("/api/v1/user/getUserByEmail/email")
    Call<UserDto> getUserByEmail(@Query("email") String email);
}
