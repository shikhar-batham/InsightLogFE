package com.example.insightlogfe.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.insightlogfe.Adapters.RecyclerGridAdapter;
import com.example.insightlogfe.R;
import com.example.insightlogfe.Constants.GridConstants;
import com.example.insightlogfe.model.ProfileModelGrid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
RecyclerView gridRecycler;
ImageView imgUser,img_user_img;

FloatingActionButton btnUserImg;
    private final int GALLERY_REQ_CODE = 1000;
    public ProfileFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView img_user_img = view.findViewById(R.id.img_user_img);
        FloatingActionButton btnUserImg = view.findViewById(R.id.btnUserImg);
        btnUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iUser = new Intent(Intent.ACTION_PICK);
                iUser.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // iUser.setType("image/*");
                startActivityForResult(iUser,GALLERY_REQ_CODE);
            }
        });
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState)
    {

//       ******** Recycler view start   ********

        super.onViewCreated(view, savedInstanceState);
        ArrayList<ProfileModelGrid> gridList = GridConstants.GridData();

        // this RecyclerView will use.
        RecyclerView recyclerView = view.findViewById(R.id.gridRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecyclerGridAdapter profileAdapter = new RecyclerGridAdapter(gridList, getContext());

        // recyclerview to inflate the items.
        recyclerView.setAdapter(profileAdapter);
        profileAdapter.notifyDataSetChanged();

//       ********* Recycler view end   ************

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(resultCode== Activity.RESULT_OK && requestCode==GALLERY_REQ_CODE){    // For Gallery
                img_user_img.setImageURI(data.getData());
            }

    }



}