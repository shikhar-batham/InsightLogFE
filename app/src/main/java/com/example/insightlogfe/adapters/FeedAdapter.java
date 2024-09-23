package com.example.insightlogfe.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insightlogfe.R;
import com.example.insightlogfe.model.FeedModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView userFeedImgIv, feedImgIv, likeBtn, shareBtn, commentBtn, optionsIv;
        private TextView feedUserNameTv, feedContentTv;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userFeedImgIv = itemView.findViewById(R.id.user_feed_img);
            feedImgIv = itemView.findViewById(R.id.feed_img);
            feedContentTv = itemView.findViewById(R.id.feed_content_tv);
            feedUserNameTv = itemView.findViewById(R.id.feed_user_name_tv);
            likeBtn = itemView.findViewById(R.id.imgLike);
            shareBtn = itemView.findViewById(R.id.imgShare);
            commentBtn = itemView.findViewById(R.id.imgComment);
            optionsIv = itemView.findViewById(R.id.options_iv);

            likeBtn.setOnClickListener(v -> likeBtn.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.appDarkColor)));

//            optionsIv.setOnClickListener(v -> showPopupMenu(itemView));
        }

        public void setPageData(Bitmap userImageBitmap, Bitmap feedImageBitmap, String userName, String content) {

            Glide.with(context)
                    .load(userImageBitmap).into(userFeedImgIv);

            Glide.with(context)
                    .load(feedImageBitmap).into(feedImgIv);

            feedUserNameTv.setText(userName);
            feedContentTv.setText(content);

            shareBtn.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getImageUriFromBitmap(context, feedImageBitmap).toString()));
                shareIntent.putExtra(Intent.EXTRA_TEXT, content);
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            });
        }
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.post_action_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Toast.makeText(view.getContext(), "Edit Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case 2:
                        Toast.makeText(view.getContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    private Uri getImageUriFromBitmap(Context context, Bitmap bitmap) {

        File file = new File(context.getCacheDir(), "shared_image.png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileProvider.getUriForFile(context, "com.example.insightlogfe.Adapters.fileprovider", file);
    }

}
