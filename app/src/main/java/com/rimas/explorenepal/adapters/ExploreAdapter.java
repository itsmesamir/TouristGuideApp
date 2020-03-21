package com.rimas.explorenepal.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.activities.Details;
import com.rimas.explorenepal.activities.MainActivity;
import com.rimas.explorenepal.activities.Map;
import com.rimas.explorenepal.fragments.MapFragment;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private Context context;
    private ArrayList<PostList> postLists;
    CardView cardView;
    ImageButton btnMap;
    Fragment MapFragment;


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
        cardView=view.findViewById(R.id.cardExploree);
        btnMap=view.findViewById(R.id.btnExploreMap);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        PostList postList=postLists.get(position);
        holder.name.setText(postList.getName());
        holder.location.setText(postList.getLocation());
        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Map.class);

                intent.putExtra("Lat", postList.getLat());
                intent.putExtra("Long", postList.getLong());
                context.startActivity(intent);

//                AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                Fragment myFragment = new MapFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.exploreFragment, myFragment).addToBackStack(null).commit();
//
//                MapFragment mapFragment=new MapFragment();
//                Bundle bundle=new Bundle();
//
//                mapFragment.setArguments(bundle);

            }
        });

        Glide.with(holder.exploreImage.getContext())
                .load(Uri.parse(postList.getImage()))
                .placeholder(holder.exploreImage.getDrawable())
                .into(holder.exploreImage);
//
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", postList.getName());
                intent.putExtra("imageDescription", postList.getDescription());
                intent.putExtra("imageLocation", postList.getLocation());
                intent.putExtra("image", postList.getImage());
                context.startActivity(intent);
            }
        });

//




//
    }

    @Override
    public int getItemCount() {

//        return postLists.size();
       return postLists == null ? 0 : postLists.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder{

        ImageView exploreImage;
        TextView name,location;
        ImageButton btnMap, btnBookmark;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);

            exploreImage=itemView.findViewById(R.id.exploreImage);
            name= itemView.findViewById(R.id.exploreName);
            location= itemView.findViewById(R.id.exploreLocation);

            btnMap= itemView.findViewById(R.id.btnExploreMap);
        }
    }
}
