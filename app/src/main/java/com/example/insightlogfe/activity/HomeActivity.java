package com.example.insightlogfe.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.insightlogfe.R;
import com.example.insightlogfe.fragments.HomeFragment;
import com.example.insightlogfe.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView homeBottomNavigationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeBottomNavigationContainer = findViewById(R.id.homeBottomNavigationContainer);
        homeBottomNavigationContainer.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    loadFrag(new ProfileFragment(), 0);
                }

                else if (id == R.id.nav_home) {
                    loadFrag(new HomeFragment(), 0);
                }

                return true;
            }
        });
        homeBottomNavigationContainer.setSelectedItemId(R.id.nav_home);
    }

    public void loadFrag(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 1)
            ft.add(R.id.homeFlContainer, fragment);
        else
            ft.replace(R.id.homeFlContainer, fragment);
        ft.commit();
    }
}