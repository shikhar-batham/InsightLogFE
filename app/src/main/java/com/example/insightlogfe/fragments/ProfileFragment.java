package com.example.insightlogfe.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.insightlogfe.Adapters.UserProfileAdapter;
import com.example.insightlogfe.Constants.IntentConstants;
import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.R;
import com.example.insightlogfe.activity.CropperActivity;
import com.example.insightlogfe.apiService.PostApi;
import com.example.insightlogfe.apiService.UserApi;
import com.example.insightlogfe.model.UserPostModel;
import com.example.insightlogfe.payload.PostDto;
import com.example.insightlogfe.payload.UserDto;
import com.example.insightlogfe.retrofit.RetrofitService;
import com.example.insightlogfe.utilities.ImageUtils;
import com.example.insightlogfe.utilities.Utilities;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements BaseFragment {
    private ImageView userProfileImage;
    private RetrofitService rfs;
    private UserApi userApi;
    private PostApi postApi;
    private int userId;
    private String authToken;
    private String userEmail;
    private PreferenceManager preferenceManager;
    private UserDto fetchedUserDto;
    public static Bitmap USER_IMG;
    private static final int IMAGE_CROPPER_REQUEST_CODE = 1001;
    private static final int IMAGE_CROPPER_RESULT_CODE = 1002;
    private ActivityResultLauncher<String> getImageContent;
    private TextView userNameTv;
    private RecyclerView profilePageRv;
    private ArrayList<UserPostModel> userPostModelList;
    private UserProfileAdapter userProfileAdapter;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getPreferences();

        this.rfs = new RetrofitService();
        this.userApi = rfs.getRetrofit().create(UserApi.class);
        this.postApi = rfs.getRetrofit().create(PostApi.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePageRv = view.findViewById(R.id.profile_page_rv);
        userProfileImage = view.findViewById(R.id.user_profile_img);
        userNameTv = view.findViewById(R.id.name_tv);

        setPageData();

        return view;

    }

    @Override
    public void setPageData() {

        logMessage("Calling user with email " + userEmail);
        userApi.getUserByEmail(userEmail).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchedUserDto = response.body();
                    logMessage("User fetched successfully!");
                    userNameTv.setText(fetchedUserDto.getUserUniqueName());
                } else {
                    logMessage("Failed to fetch user");
                }
                downloadAndSetUserProfileImage();
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                logMessage(t.getMessage());
            }
        });
    }

    private void setPostData() {
        getAllPostsUser();

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        userProfileAdapter = new UserProfileAdapter(userPostModelList);
        profilePageRv.setAdapter(userProfileAdapter);
        profilePageRv.setLayoutManager(gridLayoutManager);
    }

    private void getAllPostsUser() {

        Utilities.logError("Setting up posts for userId " + fetchedUserDto.getId());
        this.postApi.getAllPostsByUserId(fetchedUserDto.getId()).enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostDto>> call, @NonNull Response<List<PostDto>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Utilities.logMessage("Fetching posts");
                    List<PostDto> fetchedUserPostList = response.body();

                    Utilities.logMessage("Successfully fetched all posts of user with list size "+fetchedUserPostList.size());

                    for (PostDto postDto : fetchedUserPostList) {
                        userPostModelList.add(new UserPostModel(postDto.getImage()));
                    }
                    userProfileAdapter.notifyDataSetChanged();
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

//        userPostModelList = GridConstants.GridData();

    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        userProfileImage.setOnClickListener(v -> getImageContent.launch("image/*"));
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

    private void downloadAndSetUserProfileImage() {

        logMessage("Trying to download user profile image");

        if (USER_IMG != null) {
            Glide.with(requireActivity()).load(USER_IMG)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_person))
                    .into(userProfileImage);
        } else {
            userApi.downloadUserProfileImage(fetchedUserDto.getId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    Bitmap bitmap = null;
                    if (response.isSuccessful() && response.body() != null) {
                        bitmap = ImageUtils.getImageFromResponse(response);
                    }

                    if (bitmap != null) {
                        Glide.with(requireActivity()).load(bitmap)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.ic_person))
                                .into(userProfileImage);
                    } else {
                        Glide.with(requireActivity()).load(R.drawable.ic_person)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.ic_person))
                                .into(userProfileImage);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    logMessage(t.getMessage()
                            + "\n" + call.request());
                    toastErrors("image setting failed");
                }
            });
        }
        setPostData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        logMessage("started the activity result.");

        if (resultCode == IMAGE_CROPPER_RESULT_CODE && requestCode == IMAGE_CROPPER_REQUEST_CODE && data != null) {
            String imageData = data.getStringExtra(IntentConstants.RESULT_IMAGE_DATA);
            Uri uri = null;
            if (imageData != null) {
                uri = Uri.parse(imageData);
            }

            //String imagePath = getRealPathFromUri(uri);
            assert uri != null;
            File file = new File(uri.getEncodedPath());
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            logMessage("multipart body : " + body);

            Uri finalUri = uri;

            userApi.uploadUserProfileImage(fetchedUserDto.getId(), body).enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    logMessage("Uploading profile image for user with user id " + fetchedUserDto.getId());

                    if (response.isSuccessful() && response.body() != null) {
                        Glide.with(requireContext())
                                .load(finalUri)
                                .apply(new RequestOptions().placeholder(R.drawable.ic_person))
                                .into(userProfileImage);
                        logMessage("image  uploaded successfully");
                    } else {
                        logMessage(response.code()
                                + "\nimage  uploaded failed\n"
                                + response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    logMessage(t.getMessage());
                }
            });

        } else if (resultCode == UCrop.RESULT_ERROR && data != null) {

            Error error = (Error) UCrop.getError(data);
            assert error != null;
            logMessage(error.getMessage());
        }
    }

    private static void logMessage(String msg) {

        Log.i("[INFO]", "-->" + msg);
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