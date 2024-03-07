package com.example.insightlogfe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insightlogfe.R;
import com.example.insightlogfe.model.HomeFeedModelLinear;

import java.util.ArrayList;

public class RecyclerLinearFeedAdapter extends RecyclerView.Adapter<RecyclerLinearFeedAdapter.viewHolder> {
    ImageView imgfeedProfilePic,imgfeedPic,imgLike,imgComment,imgShare;
    TextView txtFeedUsername,txtFeedUserbio;
    ArrayList<HomeFeedModelLinear> feedList;
    Context context;

    public RecyclerLinearFeedAdapter(ArrayList<HomeFeedModelLinear> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerLinearFeedAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_feed_linear_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerLinearFeedAdapter.viewHolder holder, int position) {
        HomeFeedModelLinear homeFeedModelLinear = feedList.get(position);
        holder.imgfeedPic.setImageResource(homeFeedModelLinear.getPic_feed_main());
        holder.imgfeedProfilePic.setImageResource(homeFeedModelLinear.getPic_feed_profile());
        holder.txtFeedUserbio.setText(homeFeedModelLinear.getText_userbio());
        holder.txtFeedUsername.setText(homeFeedModelLinear.getText_username());

        holder.imgfeedPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Feed Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Liked the image", Toast.LENGTH_SHORT).show();
            }
        });

        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Add your Comment", Toast.LENGTH_SHORT).show();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Share your image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgfeedProfilePic,imgfeedPic;
        TextView txtFeedUsername,txtFeedUserbio;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgfeedPic = itemView.findViewById(R.id.imgfeedPic);
            imgfeedProfilePic = itemView.findViewById(R.id.imgfeedProfilePic);
            txtFeedUserbio = itemView.findViewById(R.id.txtFeedUserbio);
            txtFeedUsername = itemView.findViewById(R.id.txtFeedUsername);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgShare = itemView.findViewById(R.id.imgShare);
            imgComment = itemView.findViewById(R.id.imgComment);
        }
    }

}
