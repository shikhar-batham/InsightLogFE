package com.example.insightlogfe.apiService;

import com.example.insightlogfe.payload.PostDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {

    @POST("/api/v1/post/{userId}")
    Call<PostDto> createPost(@Body PostDto postDto, @Path("userId") Integer userId);

    @Multipart
    @POST("/api/v1/post/postImage/{postId}")
    Call<ResponseBody> uploadPostImage(@Path("postId") Integer postId,
                                       @Part MultipartBody.Part file);

    @GET("/api/v1/post/getPostsByUserId/{userId}")
    Call<List<PostDto>> getAllPostsByUserId(@Path("userId") Integer userId);

    @GET("/api/v1/post/downloadPostImage/{postId}")
    Call<ResponseBody> downloadPostImage(@Path("postId") int postId);

    @GET("/api/v1/post/getAllPosts")
    Call<List<PostDto>>getAllPosts();
}
