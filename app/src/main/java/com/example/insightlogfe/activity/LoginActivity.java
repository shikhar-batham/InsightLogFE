package com.example.insightlogfe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.R;
import com.example.insightlogfe.apiService.AuthApi;
import com.example.insightlogfe.payload.JwtAuthRequest;
import com.example.insightlogfe.payload.JwtAuthResponse;
import com.example.insightlogfe.retrofit.RetrofitService;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdt, passwordEdt;
    private Button loginButton;
    private PreferenceManager preferenceManager;
    private RetrofitService rfs;
    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getPreferences();

        this.rfs = new RetrofitService();
        authApi = rfs.getRetrofit().create(AuthApi.class);

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
        String email = emailEdt.getText().toString();
        jwtAuthRequest.setUsername(email);
        jwtAuthRequest.setPassword(passwordEdt.getText().toString());

        String finalEmail = email;
        authApi.createToken(jwtAuthRequest).enqueue(new Callback<JwtAuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<JwtAuthResponse> call, @NonNull Response<JwtAuthResponse> response) {

                if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    toastErrors("Invalid email or password");
                    return;
                }

                if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    toastErrors("User with " + emailEdt.getText().toString() + " doesn't exists.");
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {

                    preferenceManager.putString(PrefConstants.USER_AUTH_TOKEN, response.body().getToken());
                    logMessage("auth token: " + preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN));
                    logMessage("Calling login api");
                    preferenceManager.putString(PrefConstants.USER_EMAIL, finalEmail);

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

    private String toastErrors(String message) {

        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        return message;
    }

    public void getPreferences() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.clearPreferences();
    }
}