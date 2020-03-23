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
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;
import java.util.List;




public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context context;
    private List<BookmarkList> bookmarkList;
    CardView cardView;
    ImageButton btnMap;
    Fragment MapFragment;

    public BookmarkAdapter(Context context, List<BookmarkList> bookmarkList){
        this.context = context;
        this.bookmarkList = bookmarkList;
    }



    public void setPostList(List<BookmarkList> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.bookmark_layout, parent,false);

//        btnMap=view.findViewById(R.id.btnPopularMap);
//        cardView= view.findViewById(R.id.cardPopular);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {

        final BookmarkList ld= bookmarkList.get(position);

        holder.txtName.setText(ld.getName());
        holder.txtLocation.setText(ld.getLocation());
        holder.txtDescription.setText(ld.getDescription());

        Glide.with(holder.bookmark_image.getContext())
                .load(Uri.parse(ld.getImage()))
                .placeholder(holder.bookmark_image.getDrawable())
                .into(holder.bookmark_image);


    }

    @Override
    public int getItemCount() {


        return bookmarkList == null ? 0 : bookmarkList.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtDescription, txtLocation;
        ImageButton btnBookmark, btnLocation, btnFavourite;
        ImageView bookmark_image;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName= itemView.findViewById(R.id.txtName);
            txtDescription=itemView.findViewById(R.id.txtDescription);
            txtLocation=itemView.findViewById(R.id.txtLocation);
            btnBookmark=itemView.findViewById(R.id.btnBookmark);
            btnLocation= itemView.findViewById(R.id.btnLocation);
            btnFavourite=itemView.findViewById(R.id.btnFavourite);
            bookmark_image=itemView.findViewById(R.id.bookmark_image);
        }
    }
}
