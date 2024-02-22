package com.example.insightlogfe.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.insightlogfe.R;
import com.example.insightlogfe.apiService.AuthApi;
import com.example.insightlogfe.payload.JwtAuthRequest;
import com.example.insightlogfe.payload.JwtAuthResponse;
import com.example.insightlogfe.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdt, passwordEdt;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdt = findViewById(R.id.emailEdt);
        passwordEdt = findViewById(R.id.passwordEdt);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {

        JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
        jwtAuthRequest.setUsername(emailEdt.getText().toString());
        jwtAuthRequest.setPassword(passwordEdt.getText().toString());

        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);

        authApi.createToken(jwtAuthRequest).enqueue(new Callback<JwtAuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<JwtAuthResponse> call, @NonNull Response<JwtAuthResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    JwtAuthResponse jwtResponse = response.body();
                    Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
                    iHome.putExtra("token", jwtResponse.toString());
                    startActivity(iHome);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JwtAuthResponse> call, Throwable t) {
                logMessage("Failed to login " + t.getMessage());
            }
        });

    }

    private static void logMessage(String msg) {

        Log.i("[INFO]", "-->" + msg);
    }
}