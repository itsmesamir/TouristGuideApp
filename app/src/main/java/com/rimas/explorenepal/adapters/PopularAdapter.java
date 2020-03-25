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
import com.rimas.explorenepal.api.BookmarkApi;
import com.rimas.explorenepal.api.RecommendationApi;
import com.rimas.explorenepal.model.BookmarkList_Data;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;
import com.rimas.explorenepal.model.RecommendationList;
import com.rimas.explorenepal.model.SinglePost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    ImageButton btnMap;
    CardView cardView;
    RecyclerView recyclerView1;
    private ArrayList<PopularList> popularLists;


    private ArrayList<RecommendationList> recommendationLists;
    private ArrayList<SinglePost> favouriteLists;
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

        holder.btnBookmark.setTag("R.drawable.ic_bookmark");

        Call<ArrayList<SinglePost>> selectCall= BookmarkApi.getExploreService().getSingleData();
        selectCall.enqueue(new Callback<ArrayList<SinglePost>>() {
            @Override
            public void onResponse(Call<ArrayList<SinglePost>> call, Response<ArrayList<SinglePost>> response) {
                favouriteLists=response.body();

                for(int i=0; i<favouriteLists.size();i++){

                    if (popularList.getId()==favouriteLists.get(i).getId()){

                        holder.btnBookmark.setTag("R.drawable.ic_bookmark_black");
                        holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black);

                    }

                }






            }

            @Override
            public void onFailure(Call<ArrayList<SinglePost>> call, Throwable t) {

                Toast.makeText(context, "No posts", Toast.LENGTH_SHORT).show();

            }
        });

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


        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(holder.btnBookmark.getTag().equals("R.drawable.ic_bookmark")){
                    holder.btnBookmark.setTag("R.drawable.ic_bookmark_black");
                    Toast.makeText(context, "Added to bookmark", Toast.LENGTH_SHORT).show();

                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black);
                    Call<BookmarkList_Data> newCall = RecommendationApi.getExploreService().savePost(popularList.getId(),popularList.getName(),popularList.getLocation()
                            ,popularList.getDescription(),popularList.getLat(), popularList.getLong(), popularList.getImage());
                    newCall.enqueue(new Callback<BookmarkList_Data>() {
                        @Override
                        public void onResponse(Call<BookmarkList_Data> call, Response<BookmarkList_Data> response) {
                            Toast.makeText(context, "Success in inserting babe", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<BookmarkList_Data> call, Throwable t) {

//                               Toast.makeText(context, "Oopss. sorry", Toast.LENGTH_SHORT).show();
//                               Log.e("failure", String.valueOf(t.getCause()));

                        }
                    });

                }
                else{
                    holder.btnBookmark.setTag("R.drawable.ic_bookmark");

                    Toast.makeText(context, "Removed from bookmark", Toast.LENGTH_SHORT).show();

                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);

                    Call<BookmarkList_Data> deleteData= RecommendationApi.getExploreService().deletePost(popularList.getId());
                    deleteData.enqueue(new Callback<BookmarkList_Data>() {
                        @Override
                        public void onResponse(Call<BookmarkList_Data> call, Response<BookmarkList_Data> response) {

//                               Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<BookmarkList_Data> call, Throwable t) {

//                               Toast.makeText(context, "Unable to delete.", Toast.LENGTH_SHORT).show();


                        }
                    });



                }




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
        ImageButton btnMap, btnBookmark;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImage=itemView.findViewById(R.id.popular_image);
            popularName= itemView.findViewById(R.id.popular_name);
            popularLocation= itemView.findViewById(R.id.popular_location);
            popularDescription= itemView.findViewById(R.id.popularDescription);
            btnBookmark= itemView.findViewById(R.id.btnPopularBookmark);


            btnMap= itemView.findViewById(R.id.btnPopularMap);
        }
    }
}
