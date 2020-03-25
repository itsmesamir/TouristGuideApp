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
import com.rimas.explorenepal.model.BookmarkList;
import com.rimas.explorenepal.model.BookmarkList_Data;
import com.rimas.explorenepal.model.FavouriteList;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;
import java.util.List;




public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context context;
    private List<FavouriteList> bookmarkList;
    CardView cardView;
//    ImageButton btnLocation;
    Fragment MapFragment;

    public BookmarkAdapter(Context context, List<FavouriteList> bookmarkList){
        this.context = context;
        this.bookmarkList = bookmarkList;
    }



    public void setPostList(List<FavouriteList> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.bookmark_layout, parent,false);

//        btnLocation= view.findViewById(R.id.btnMapBk);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {

        FavouriteList favouriteList= bookmarkList.get(position);

        holder.btnLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Map.class);

                intent.putExtra("Lat", favouriteList.getLat());
                intent.putExtra("Long", favouriteList.getLong());
                context.startActivity(intent);
            }
        });

        holder.txtName.setText(favouriteList.getName());
        holder.txtLocation.setText(favouriteList.getLocation());

        Glide.with(holder.bookmark_image.getContext())
                .load(Uri.parse(favouriteList.getImage()))
                .placeholder(holder.bookmark_image.getDrawable())
                .into(holder.bookmark_image);




        holder.bookmark_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", favouriteList.getName());
                intent.putExtra("imageDescription", favouriteList.getDescription());
                intent.putExtra("imageLocation", favouriteList.getLocation());
                intent.putExtra("image", favouriteList.getImage());
                context.startActivity(intent);
            }
        });
        holder.txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", favouriteList.getName());
                intent.putExtra("imageDescription", favouriteList.getDescription());
                intent.putExtra("imageLocation", favouriteList.getLocation());
                intent.putExtra("image", favouriteList.getImage());
                context.startActivity(intent);
            }
        });
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Details.class);
                intent.putExtra("imageName", favouriteList.getName());
                intent.putExtra("imageDescription", favouriteList.getDescription());
                intent.putExtra("imageLocation", favouriteList.getLocation());
                intent.putExtra("image", favouriteList.getImage());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {


        return bookmarkList == null ? 0 : bookmarkList.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtDescription, txtLocation;
        ImageButton btnBookmark, btnLocation1, btnFavourite;
        ImageView bookmark_image;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName= itemView.findViewById(R.id.txtBkName);
            txtLocation=itemView.findViewById(R.id.txtBkLocation);
            btnBookmark=itemView.findViewById(R.id.btnBookmarkBk);
            btnLocation1= itemView.findViewById(R.id.btnMapBk);
            bookmark_image=itemView.findViewById(R.id.imgBookmark);
        }
    }
}
