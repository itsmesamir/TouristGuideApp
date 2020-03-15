package com.rimas.explorenepal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private Context context;
    private ArrayList<PostList> postLists;


    public ExploreAdapter(Context context, ArrayList<PostList> postLists) {
        this.context = context;
        this.postLists = postLists;

    }
    public void setPostList(ArrayList<PostList> postLists) {
        this.postLists = postLists;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.explore_top_layout, parent,false);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        PostList postList=postLists.get(position);
        holder.name.setText(postList.getName());
        holder.location.setText(postList.getLocation());
//        Log.e("Name is " , String.valueOf(postList.getName()));
//        holder.name.setText("samir");
//        holder.location.setText("Sagarmatha");
//        Log.e("URL is :" , postList.getImage());

        Glide.with(holder.exploreImage.getContext())
                .load(Uri.parse(postList.getImage()))
                .placeholder(holder.exploreImage.getDrawable())
                .into(holder.exploreImage);
//        Glide.with(context).load(postList.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.bg).into(holder.exploreImage);

//        holder.exploreImage.buildDrawingCache();
//        bitmap = holder.exploreImage.getDrawingCache();
//            holder.exploreImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {

//        return postLists.size();
       return postLists == null ? 0 : postLists.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder{

        ImageView exploreImage;
        TextView name,location;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);

            exploreImage=itemView.findViewById(R.id.exploreImage);
            name= itemView.findViewById(R.id.exploreName);
            location= itemView.findViewById(R.id.exploreLocation);
        }
    }
}
