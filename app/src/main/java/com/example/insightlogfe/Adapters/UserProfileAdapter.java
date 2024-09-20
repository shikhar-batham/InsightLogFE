package com.example.insightlogfe.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.R;
import com.example.insightlogfe.model.UserPostModel;

import java.util.ArrayList;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.viewHolder> {

    private ArrayList<UserPostModel> userPostModelList;
    private Context context;

    public UserProfileAdapter(ArrayList<UserPostModel> userPostModelList) {
        this.userPostModelList = userPostModelList;
    }

    @NonNull
    @Override
    public UserProfileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_profile_grid_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String image = userPostModelList.get(position).getImage();

        holder.setData(image);
    }

    @Override
    public int getItemCount() {
        return userPostModelList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView postImageIv;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            postImageIv = itemView.findViewById(R.id.post_img);
        }

        public void setData(String image) {
            postImageIv.setImageURI(Uri.parse(image));
        }
    }

}
