package com.example.insightlogfe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.insightlogfe.Adapters.RecyclerGridAdapter;
import com.example.insightlogfe.R;
import com.example.insightlogfe.Constants.GridConstants;
import com.example.insightlogfe.model.ProfileModelGrid;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ImageView userProfilePicture;

    RecyclerView gridRecycler;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);   // getting the employeelist
        ArrayList<ProfileModelGrid> gridList = GridConstants.GridData();
        // Assign employeelist to ItemAdapter

        // Set the LayoutManager that
        // this RecyclerView will use.
        RecyclerView recyclerView = view.findViewById(R.id.gridRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RecyclerGridAdapter profileAdapter = new RecyclerGridAdapter(gridList, getContext());
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.setAdapter(profileAdapter);
        profileAdapter.notifyDataSetChanged();
    }


}