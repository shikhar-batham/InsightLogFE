package com.example.insightlogfe.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.adapters.FeedAdapter;
import com.example.insightlogfe.Constants.IntentConstants;
import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.R;
import com.example.insightlogfe.apiService.PostApi;
import com.example.insightlogfe.apiService.UserApi;
import com.example.insightlogfe.model.FeedModel;
import com.example.insightlogfe.payload.PostDto;
import com.example.insightlogfe.retrofit.RetrofitService;
import com.example.insightlogfe.utilities.ImageUtils;
import com.example.insightlogfe.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAllPostActivity extends AppCompatActivity implements BaseActivity {
    private RecyclerView allPostRv;
    private PreferenceManager preferenceManager;
    private String authToken;
    private int userId;
    private String userEmail;
    private PostApi postApi;
    private UserApi userApi;
    private RetrofitService rfs;
    private ArrayList<FeedModel> postModelList;
    private FeedAdapter feedAdapter;
    private ProgressBar allPostPgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferences();

        rfs = new RetrofitService();
        postApi=rfs.getRetrofit().create(PostApi.class);
        userApi=rfs.getRetrofit().create(UserApi.class);

        setContentView(R.layout.activity_user_all_post);

        allPostRv = findViewById(R.id.all_post_rv);
        allPostPgBar = findViewById(R.id.all_post_pg_bar);

        allPostPgBar.setVisibility(View.VISIBLE);

        postModelList = new ArrayList<>();

        setPageData();
    }

    private void getIntents() {
        Intent intent = getIntent();
        userId= intent.getIntExtra(IntentConstants.INTENT_USER_ID,-1);
        Utilities.logMessage("User id from intent " + userId);
    }

    private void setPageData() {
        getIntents();
        getAllPostsUser();
    }

    private void getAllPostsUser() {

        Utilities.logError("Setting up posts for userId " + userId);

        this.postApi.getAllPostsByUserId(userId).enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostDto>> call, @NonNull Response<List<PostDto>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Utilities.logMessage("Fetching posts");
                    List<PostDto> fetchedUserPostList = response.body();

                    Utilities.logMessage("Successfully fetched all posts of user with list size " + fetchedUserPostList.size());

                    for (PostDto postDto : fetchedUserPostList) {
                        postApi.downloadPostImage(postDto.getPostId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                Bitmap fetchedPostImageBitmap = null;
                                if (response.isSuccessful() && response.body() != null) {
                                    fetchedPostImageBitmap = ImageUtils.getImageFromResponse(response);
                                    Utilities.logMessage("Fetched post image with post id " + postDto.getPostId());

                                    Bitmap finalFetchedPostImageBitmap = fetchedPostImageBitmap;
                                    userApi.downloadUserProfileImage(postDto.getUser().getId()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                            Bitmap userImageBitmap = null;
                                            if (response.isSuccessful() && response.body() != null) {
                                                userImageBitmap = ImageUtils.getImageFromResponse(response);
                                                Utilities.logMessage("User profile image downloaded for feed");
                                            } else {
                                                Utilities.logMessage("Failed to download user profile image for feed");
                                            }

                                            postModelList.add(new FeedModel(userImageBitmap, finalFetchedPostImageBitmap, postDto.getUser().getUserUniqueName(), postDto.getContent()));
                                            feedAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                            Utilities.logMessage(t.getMessage());
                                        }
                                    });
                                } else {
                                    Utilities.logMessage("Failed to fetch image of the post with postId " + postDto.getPostId());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                Utilities.logMessage(t.getMessage());
                            }
                        });
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    feedAdapter = new FeedAdapter(postModelList, getApplicationContext());
                    allPostRv.setAdapter(feedAdapter);
                    allPostRv.setLayoutManager(linearLayoutManager);
                } else {
                    Utilities.logApiError(response);
                }
            }

            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {
                Utilities.logMessage("Failed to fetch all posts.");
                Utilities.logMessage(t.getMessage());
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                allPostPgBar.setVisibility(View.GONE);
            }
        }, 5000);
    }

    @Override
    public void getPreferences() {
        preferenceManager = new PreferenceManager(getApplicationContext());
//        if (preferenceManager.getString(PrefConstants.U_USER_ID) != null)
//            userId = Integer.parseInt(preferenceManager.getString(PrefConstants.U_USER_ID));
        userEmail = preferenceManager.getString(PrefConstants.USER_EMAIL);
        authToken = preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN);
    }
}