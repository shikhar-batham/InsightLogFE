package com.example.insightlogfe.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.Adapters.FeedAdapter;
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


public class HomeFragment extends Fragment implements BaseFragment {
    private PreferenceManager preferenceManager;
    private int userId;
    private String authToken;
    private RetrofitService rfs;
    private UserApi userApi;
    private PostApi postApi;
    private ArrayList<FeedModel> feedModelList;
    private RecyclerView feedRv;
    private FeedAdapter feedAdapter;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getPreferences();

        rfs = new RetrofitService();
        userApi = rfs.getRetrofit().create(UserApi.class);
        postApi = rfs.getRetrofit().create(PostApi.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        feedRv = view.findViewById(R.id.feed_rv);

        feedModelList = new ArrayList<>();

        setPageData();

        return view;
    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void setPageData() {
        getPageData();
    }

    private void getPageData() {

        this.postApi.getAllPosts().enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostDto>> call, @NonNull Response<List<PostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PostDto> fetchedPosts = response.body();
                    Utilities.logMessage("fetched posts for feed");

                    for (PostDto postDto : fetchedPosts) {
                        postApi.downloadPostImage(postDto.getPostId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                Bitmap postImageBitmap = null;
                                if (response.isSuccessful() && response.body() != null) {
                                    postImageBitmap = ImageUtils.getImageFromResponse(response);
                                    Utilities.logMessage("Downloaded post image for feed");
                                    Bitmap finalPostImageBitmap = postImageBitmap;
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

                                            feedModelList.add(new FeedModel(userImageBitmap, finalPostImageBitmap, postDto.getUser().getUserUniqueName(), postDto.getContent()));
                                            feedAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                            Utilities.logMessage(t.getMessage());
                                        }
                                    });
                                    Utilities.logMessage("Fetched post image with post id " + postDto.getPostId());
                                } else {
                                    Utilities.logMessage("Failed to fetch image of the post with postId " + postDto.getPostId());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    feedAdapter = new FeedAdapter(feedModelList, getContext());
                    feedRv.setAdapter(feedAdapter);
                    feedRv.setLayoutManager(linearLayoutManager);
                } else {
                    Utilities.logMessage("Could not fetch posts");
                }
            }

            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {

            }
        });

    }

    @Override
    public void getPreferences() {
        preferenceManager = new PreferenceManager(requireActivity());
        if (preferenceManager.getString(PrefConstants.U_USER_ID) != null)
            userId = Integer.parseInt(preferenceManager.getString(PrefConstants.U_USER_ID));
        authToken = preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN);
    }

    @Override
    public String toastErrors(String message) {
        return null;
    }


}