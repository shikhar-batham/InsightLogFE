package com.example.insightlogfe.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insightlogfe.R;
import com.example.insightlogfe.model.FeedModel;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.viewHolder> {

    private ArrayList<FeedModel> feedModelList;
    private Context context;

    public FeedAdapter(ArrayList<FeedModel> feedModelList, Context context) {
        this.feedModelList = feedModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_feed_linear_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.viewHolder holder, int position) {

        Bitmap userImageBitmap = feedModelList.get(position).getUserImageBitmap();
        Bitmap feedImageBitmap = feedModelList.get(position).getFeedImageBitmap();
        String userName = feedModelList.get(position).getUserName();
        String content = feedModelList.get(position).getContent();

        holder.setPageData(userImageBitmap, feedImageBitmap, userName, content);
    }

    @Override
    public int getItemCount() {
        return feedModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView userFeedImgIv, feedImgIv;
        private TextView feedUserNameTv, feedContentTv;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userFeedImgIv = itemView.findViewById(R.id.user_feed_img);
            feedImgIv = itemView.findViewById(R.id.feed_img);
            feedContentTv = itemView.findViewById(R.id.feed_content_tv);
            feedUserNameTv = itemView.findViewById(R.id.feed_user_name_tv);
        }

        public void setPageData(Bitmap userImageBitmap, Bitmap feedImageBitmap, String userName, String content) {

            Glide.with(context)
                    .load(userImageBitmap).into(userFeedImgIv);

            Glide.with(context)
                    .load(feedImageBitmap).into(feedImgIv);

            feedUserNameTv.setText(userName);
            feedContentTv.setText(content);
        }
    }

}
