package com.example.insightlogfe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.insightlogfe.R;
import com.example.insightlogfe.apiService.AuthApi;
import com.example.insightlogfe.payload.UserDto;
import com.example.insightlogfe.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameEdt, lastNameEdt, usernameEdt, emailEdt, passwordEdt, confirmPasswordEt;
    private Button sighUpButton;
    private RetrofitService rfs;
    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.rfs = new RetrofitService();
        authApi = rfs.getRetrofit().create(AuthApi.class);

        firstNameEdt = findViewById(R.id.firstName);
        lastNameEdt = findViewById(R.id.lastName);
        usernameEdt = findViewById(R.id.username);
        emailEdt = findViewById(R.id.email);
        passwordEdt = findViewById(R.id.password);
        confirmPasswordEt = findViewById(R.id.confirmPassword);
        sighUpButton = findViewById(R.id.sighUpButton);

        sighUpButton.setOnClickListener(v -> registerUser());

    }

    private void registerUser() {

        String password = passwordEdt.getText().toString();
        String confirmPassword = confirmPasswordEt.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password & Confirm password are not same", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDto userDto = createUserDto();
        authApi.registerNewUser(userDto).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto apiUserDto = response.body();
                    logMessage("User Registered successfully with email " + userDto.getEmail());
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                logMessage("Api response Error " + t.getMessage());
            }
        });

    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName(firstNameEdt.getText().toString());
        userDto.setLastName(lastNameEdt.getText().toString());
        userDto.setEmail(emailEdt.getText().toString());
        userDto.setPassword(passwordEdt.getText().toString());
        userDto.setUserUniqueName(usernameEdt.getText().toString());
        return userDto;
    }

    private static void logMessage(String msg) {

        Log.i("[INFO]", "-->" + msg);
    }
}