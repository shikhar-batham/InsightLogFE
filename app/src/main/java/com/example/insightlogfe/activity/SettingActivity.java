package com.example.insightlogfe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.R;

public class SettingActivity extends AppCompatActivity implements BaseActivity {

    private TextView updateProfileBtn, deleteAcBtn, logOutBtn;
    private PreferenceManager preferenceManager;
    private String authToken;
    private int userId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getPreferences();

        updateProfileBtn = findViewById(R.id.update_profile_btn);
        deleteAcBtn = findViewById(R.id.delete_ac_btn);
        logOutBtn = findViewById(R.id.log_out_btn);

        logOutBtn.setOnClickListener(v -> {
//            logOutBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.appDarkColor));
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void getPreferences() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getString(PrefConstants.U_USER_ID) != null)
            userId = Integer.parseInt(preferenceManager.getString(PrefConstants.U_USER_ID));
        userEmail = preferenceManager.getString(PrefConstants.USER_EMAIL);
        authToken = preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN);
    }
}