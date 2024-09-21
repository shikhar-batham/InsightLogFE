package com.example.insightlogfe.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.insightlogfe.Constants.IntentConstants;
import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.R;
import com.example.insightlogfe.activity.CropperActivity;
import com.example.insightlogfe.apiService.PostApi;
import com.example.insightlogfe.apiService.UserApi;
import com.example.insightlogfe.payload.PostDto;
import com.example.insightlogfe.payload.UserDto;
import com.example.insightlogfe.retrofit.RetrofitService;
import com.example.insightlogfe.utilities.Utilities;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePostFragment extends Fragment implements BaseFragment {
    private PreferenceManager preferenceManager;
    private String authToken;
    private int userId;
    private String userEmail;
    private Button createAPostBtn;
    private RetrofitService rfs;
    private UserApi userApi;
    private PostApi postApi;
    private static final int IMAGE_CROPPER_REQUEST_CODE = 1001;
    private static final int IMAGE_CROPPER_RESULT_CODE = 1002;
    private ActivityResultLauncher<String> getImageContent;
    private UserDto fetchedUserDto;
    private PostDto createdPostDto;
    private ImageView imgPostIv;
    private EditText postContentTv;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getPreferences();

        this.rfs = new RetrofitService();
        this.userApi = rfs.getRetrofit().create(UserApi.class);
        this.postApi = rfs.getRetrofit().create(PostApi.class);

        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        createAPostBtn = view.findViewById(R.id.create_post_btn);
        imgPostIv = view.findViewById(R.id.img_post_iv);
        postContentTv = view.findViewById(R.id.post_content_tv);

        setPageData();


        return view;
    }

    private void getPageData() {

        this.userApi.getUserByEmail(userEmail).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchedUserDto = response.body();
                    Utilities.logMessage("Successfully fetched user data in create a post fragment");
                } else {
                    Utilities.logApiResponse(response);
                    Utilities.logMessage("Failed to fetch user data in crete a post fragment");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                Utilities.logError(t.getMessage());
            }
        });
    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        imgPostIv.setOnClickListener(v -> getImageContent.launch("image/*"));

        getImageContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result == null) return;
                Intent intent = new Intent(getContext(), CropperActivity.class);
                intent.putExtra(IntentConstants.IMAGE_DATA, result.toString());
                startActivityForResult(intent, IMAGE_CROPPER_REQUEST_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_CROPPER_RESULT_CODE && requestCode == IMAGE_CROPPER_REQUEST_CODE && data != null) {
            String imageData = data.getStringExtra(IntentConstants.RESULT_IMAGE_DATA);
            Uri uri = null;
            if (imageData != null) {
                uri = Uri.parse(imageData);
            }

            assert uri != null;
            File file = new File(uri.getEncodedPath());
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            Utilities.logMessage("multipart body : " + body);

            Uri finalUri = uri;

            imgPostIv.setImageURI(finalUri);

            createAPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAPost(finalUri, body);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.homeFlContainer, new ProfileFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

//            this.postApi.createPost(postDto, fetchedUserDto.getId()).enqueue(new Callback<PostDto>() {
//                @Override
//                public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        createdPostDto = response.body();
//
//                        Utilities.logMessage("uploading image for the created post with postId " + createdPostDto.getPostId());
//
//                        postApi.uploadPostImage(createdPostDto.getPostId(), body).enqueue(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                                if (response.isSuccessful() && response.body() != null) {
//                                    Utilities.logMessage("Successfully uploaded image");
//                                } else {
//                                    Utilities.logMessage(response.code()
//                                            + "\nimage  uploaded failed\n"
//                                            + response.body());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                                Utilities.logMessage(t.getMessage());
//                            }
//                        });
//                    } else {
//                        Utilities.logApiResponse(response);
//                        Utilities.logMessage("Error while creating post");
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
//                    Utilities.logMessage(t.getMessage());
//                }
//            });

        }
    }

    private void createAPost(Uri uri, MultipartBody.Part body) {

        PostDto postDto = PostDto.builder()
                .user(fetchedUserDto)
                .title("")
                .content(postContentTv.getText().toString())
                .build();

        Utilities.logMessage("Trying to create post with userId " + fetchedUserDto.getId());

        this.postApi.createPost(postDto, fetchedUserDto.getId()).enqueue(new Callback<PostDto>() {
            @Override
            public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    createdPostDto = response.body();

                    Utilities.logMessage("uploading image for the created post with postId " + createdPostDto.getPostId());

                    postApi.uploadPostImage(createdPostDto.getPostId(), body).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Utilities.logMessage("Successfully uploaded image");
                            } else {
                                Utilities.logMessage(response.code()
                                        + "\nimage  uploaded failed\n"
                                        + response.body());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Utilities.logMessage(t.getMessage());
                        }
                    });
                } else {
                    Utilities.logApiResponse(response);
                    Utilities.logMessage("Error while creating post");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                Utilities.logMessage(t.getMessage());
            }
        });


    }


    @Override
    public void setPageData() {
        getPageData();
    }

    @Override
    public void getPreferences() {
        preferenceManager = new PreferenceManager(requireActivity());
        if (preferenceManager.getString(PrefConstants.U_USER_ID) != null)
            userId = Integer.parseInt(preferenceManager.getString(PrefConstants.U_USER_ID));
        userEmail = preferenceManager.getString(PrefConstants.USER_EMAIL);
        authToken = preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN);
    }

    @Override
    public String toastErrors(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        return message;
    }

}