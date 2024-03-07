package com.example.insightlogfe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.R;
import com.example.insightlogfe.model.StoryModelLinear;

import java.util.ArrayList;

public class RecyclerStoryAdapter extends RecyclerView.Adapter<RecyclerStoryAdapter.viewHolder>{
    
ArrayList<StoryModelLinear> storyList ;
Context context; 
ImageView imgStory;

    public RecyclerStoryAdapter(ArrayList<StoryModelLinear> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerStoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.recycler_story_linear_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StoryModelLinear storyModelLinear = storyList.get(position);
        holder.imgStory.setImageResource(storyModelLinear.getStory_pic());
        
        holder.imgStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Upload your story", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgStory;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.imgStory);
        }
    }

}


