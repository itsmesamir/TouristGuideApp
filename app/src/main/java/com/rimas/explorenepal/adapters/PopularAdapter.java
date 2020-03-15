package com.rimas.explorenepal.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    private ArrayList<PopularList> popularLists;
    public PopularAdapter( Context context, ArrayList<PopularList> popularLists){
        this.context=context;
        this.popularLists= popularLists;

    }
    public void setPostLists(ArrayList<PopularList> popularLists) {
        this.popularLists = popularLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.popular_view, parent,false);
        return new PopularViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {

        PopularList popularList=popularLists.get(position);
        holder.popularName.setText(popularList.getName());
        holder.popularLocation.setText(popularList.getLocation());
        holder.popularDescription.setText(popularList.getDescription());
//        Log.e("Name is " , String.valueOf(postList.getName()));
//        holder.name.setText("samir");
//        holder.location.setText("Sagarmatha");
//        Log.e("URL is :" , postList.getImage());

        Glide.with(holder.popularImage.getContext())
                .load(Uri.parse(popularList.getImage()))
                .placeholder(holder.popularImage.getDrawable())
                .into(holder.popularImage);

    }

 
    @Override
    public int getItemCount() {
        return popularLists == null ? 0 : popularLists.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        TextView popularName, popularLocation, popularDescription;
        ImageView popularImage;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImage=itemView.findViewById(R.id.popular_image);
            popularName= itemView.findViewById(R.id.popular_name);
            popularLocation= itemView.findViewById(R.id.popular_location);
            popularDescription= itemView.findViewById(R.id.popularDescription);
        }
    }
}
