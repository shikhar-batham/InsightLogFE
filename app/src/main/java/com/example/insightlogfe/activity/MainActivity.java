package com.example.insightlogfe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.insightlogfe.R;
import com.example.insightlogfe.model.ChildModel;
import com.example.insightlogfe.model.ParentModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp, btnHome;

    RecyclerView recyclerView;

    ArrayList<ParentModel> parentModelArrayList;
    ArrayList<ChildModel> childModelArrayList;
    ArrayList<ChildModel> favoriteList;

    ArrayList<ChildModel> hotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnHome = findViewById(R.id.btnHome);

//        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.rv_maim);
        childModelArrayList = new ArrayList<>();
        parentModelArrayList = new ArrayList<>();
        favoriteList = new ArrayList<>();
        hotList = new ArrayList<>();

        ParentAdapter parentAdapter;

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));
        childModelArrayList.add(new ChildModel(R.drawable.a));

        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));
        favoriteList.add(new ChildModel(R.drawable.e));

        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));
        hotList.add(new ChildModel(R.drawable.l));


        parentModelArrayList.add(new ParentModel("This is title", childModelArrayList));
        parentModelArrayList.add(new ParentModel("Favorite", favoriteList));
        parentModelArrayList.add(new ParentModel("Hot", hotList));

        parentAdapter = new ParentAdapter(parentModelArrayList, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();
    }
}