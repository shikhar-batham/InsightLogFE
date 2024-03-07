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
import com.example.insightlogfe.model.ProfileModelGrid;

import java.util.ArrayList;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.viewHolder>{
    ImageView imgProfilePic;
    ArrayList<ProfileModelGrid> gridList;
    Context context;

    public RecyclerGridAdapter(ArrayList<ProfileModelGrid>gridList,Context context){
        this.gridList = gridList;
        this.context=context;
    }


    @NonNull
    @Override
    public RecyclerGridAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_profile_grid_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProfileModelGrid modelGrid =gridList.get(position);
        holder.imgProfilePic.setImageResource(modelGrid.getPic());
        
        holder.imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gridList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfilePic;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfilePic = itemView.findViewById(R.id.imgProfilePic);
        }
    }

}
