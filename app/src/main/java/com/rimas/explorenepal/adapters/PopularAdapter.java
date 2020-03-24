package com.rimas.explorenepal.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.activities.Details;
import com.rimas.explorenepal.activities.Map;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    ImageButton btnMap;
    CardView cardView;
    RecyclerView recyclerView1;
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

        btnMap=view.findViewById(R.id.btnPopularMap);
        cardView= view.findViewById(R.id.cardPopular);
        return new PopularViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {

        PopularList popularList=popularLists.get(position);
        holder.popularName.setText(popularList.getName());
        holder.popularLocation.setText(popularList.getLocation());
        holder.popularDescription.setText(popularList.getDescription());

        Glide.with(holder.popularImage.getContext())
                .load(Uri.parse(popularList.getImage()))
                .placeholder(holder.popularImage.getDrawable())
                .into(holder.popularImage);

        holder.popularImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", popularList.getName());
                intent.putExtra("imageDescription", popularList.getDescription());
                intent.putExtra("imageLocation", popularList.getLocation());
                intent.putExtra("image", popularList.getImage());
                context.startActivity(intent);
            }
        });holder.popularName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", popularList.getName());
                intent.putExtra("imageDescription", popularList.getDescription());
                intent.putExtra("imageLocation", popularList.getLocation());
                intent.putExtra("image", popularList.getImage());
                context.startActivity(intent);
            }
        });
        holder.popularLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", popularList.getName());
                intent.putExtra("imageDescription", popularList.getDescription());
                intent.putExtra("imageLocation", popularList.getLocation());
                intent.putExtra("image", popularList.getImage());
                context.startActivity(intent);
            }
        });
        holder.popularDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", popularList.getName());
                intent.putExtra("imageDescription", popularList.getDescription());
                intent.putExtra("imageLocation", popularList.getLocation());
                intent.putExtra("image", popularList.getImage());
                context.startActivity(intent);
            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Map.class);

                intent.putExtra("Lat", popularList.getLat());
                intent.putExtra("Long", popularList.getLong());
                context.startActivity(intent);
            }
        });

    }


 
    @Override
    public int getItemCount() {
        return popularLists == null ? 0 : popularLists.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        TextView popularName, popularLocation, popularDescription;
        ImageView popularImage;
        ImageButton btnMap;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImage=itemView.findViewById(R.id.popular_image);
            popularName= itemView.findViewById(R.id.popular_name);
            popularLocation= itemView.findViewById(R.id.popular_location);
            popularDescription= itemView.findViewById(R.id.popularDescription);


            btnMap= itemView.findViewById(R.id.btnPopularMap);
        }
    }
}
