package com.example.insightlogfe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.Adapters.RecyclerLinearFeedAdapter;
import com.example.insightlogfe.Adapters.RecyclerStoryAdapter;
import com.example.insightlogfe.Constants.FeedConstants;
import com.example.insightlogfe.Constants.PrefConstants;
import com.example.insightlogfe.Constants.PreferenceManager;
import com.example.insightlogfe.Constants.StoryConstants;
import com.example.insightlogfe.R;
import com.example.insightlogfe.apiService.UserApi;
import com.example.insightlogfe.model.HomeFeedModelLinear;
import com.example.insightlogfe.model.StoryModelLinear;
import com.example.insightlogfe.retrofit.RetrofitService;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements BaseFragment {

    RecyclerView feedRecycler, storyRecycler;

    private PreferenceManager preferenceManager;
    private int userId;
    private String authToken;
    private RetrofitService rfs;
    private UserApi userApi;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getPreferences();

        rfs = new RetrofitService();
        userApi = rfs.getRetrofit().create(UserApi.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setPageData();

        return view;
    }

    @Override
    public void
    onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<HomeFeedModelLinear> feedList = FeedConstants.FeedData();
        ArrayList<StoryModelLinear> storyList = StoryConstants.StoryData();

        //          *****  Recycler view for stories ******
        RecyclerView storyRecycler = view.findViewById(R.id.storyRecycler);
        storyRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerStoryAdapter recyclerStoryAdapter = new RecyclerStoryAdapter(storyList, getContext());
        storyRecycler.setAdapter(recyclerStoryAdapter);
        recyclerStoryAdapter.notifyDataSetChanged();

        //   ***** Recycler View for feeds ******
        RecyclerView feedRecycler = view.findViewById(R.id.feedRecycler);
        feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerLinearFeedAdapter recyclerLinearFeedAdapter = new RecyclerLinearFeedAdapter(feedList, getContext());
        feedRecycler.setAdapter(recyclerLinearFeedAdapter);
        recyclerLinearFeedAdapter.notifyDataSetChanged();


    }

    @Override
    public void setPageData() {

    }

    @Override
    public void getPreferences() {
        preferenceManager = new PreferenceManager(requireActivity());
        if (preferenceManager.getString(PrefConstants.U_USER_ID) != null)
            userId = Integer.parseInt(preferenceManager.getString(PrefConstants.U_USER_ID));
        authToken = preferenceManager.getString(PrefConstants.USER_AUTH_TOKEN);
    }

    @Override
    public String toastErrors(String message) {
        return null;
    }


}