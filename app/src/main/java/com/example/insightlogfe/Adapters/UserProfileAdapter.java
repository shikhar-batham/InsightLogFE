package com.example.insightlogfe.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insightlogfe.R;
import com.example.insightlogfe.model.UserPostModel;

import java.util.ArrayList;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.viewHolder> {

    private ArrayList<UserPostModel> userPostModelList;
    private Context context;
    public static Bitmap POST_IMAGE;

    public UserProfileAdapter(ArrayList<UserPostModel> userPostModelList, Context context) {
        this.userPostModelList = userPostModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserProfileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_profile_grid_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Bitmap bitmap = userPostModelList.get(position).getImageBitmap();

        holder.setData(bitmap);
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

        public void setData(Bitmap bitmap) {

            Glide.with(context).load(bitmap)
                    .into(postImageIv);
        }
    }

}
