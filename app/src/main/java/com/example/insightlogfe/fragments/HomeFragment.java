package com.example.insightlogfe.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.insightlogfe.Adapters.RecyclerLinearFeedAdapter;
import com.example.insightlogfe.Adapters.RecyclerStoryAdapter;
import com.example.insightlogfe.Constants.FeedConstants;
import com.example.insightlogfe.Constants.StoryConstants;
import com.example.insightlogfe.R;
import com.example.insightlogfe.model.HomeFeedModelLinear;
import com.example.insightlogfe.model.StoryModelLinear;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    RecyclerView feedRecycler,storyRecycler;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        feedRecycler=view.findViewById(R.id.feedRecycler);
//        storyRecycler=view.findViewById(R.id.storyRecycler);
//
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        feedRecycler.setLayoutManager(horizontalLayoutManager);
//        RecyclerLinearFeedAdapter horizontalAdapter = new RecyclerLinearFeedAdapter(/* pass your data here */);
//        feedRecycler.setAdapter(horizontalAdapter);

        return view;
    }
    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<HomeFeedModelLinear> feedList = FeedConstants.FeedData();
        ArrayList<StoryModelLinear> storyList = StoryConstants.StoryData();

        //          *****  Recycler view for stories ******
        RecyclerView storyRecycler = view.findViewById(R.id.storyRecycler);
        storyRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        RecyclerStoryAdapter recyclerStoryAdapter = new RecyclerStoryAdapter(storyList,getContext());
        storyRecycler.setAdapter(recyclerStoryAdapter);
        recyclerStoryAdapter.notifyDataSetChanged();

        //   ***** Recycler View for feeds ******
        RecyclerView feedRecycler = view.findViewById(R.id.feedRecycler);
        feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerLinearFeedAdapter recyclerLinearFeedAdapter = new RecyclerLinearFeedAdapter(feedList,getContext());
        feedRecycler.setAdapter(recyclerLinearFeedAdapter);
        recyclerLinearFeedAdapter.notifyDataSetChanged();


    }
}