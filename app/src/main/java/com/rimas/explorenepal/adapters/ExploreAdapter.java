package com.rimas.explorenepal.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.activities.Details;
import com.rimas.explorenepal.activities.MainActivity;
import com.rimas.explorenepal.activities.Map;
import com.rimas.explorenepal.api.BookmarkApi;
import com.rimas.explorenepal.api.RecommendationApi;
import com.rimas.explorenepal.fragments.ExploreFragment;
import com.rimas.explorenepal.fragments.MapFragment;
import com.rimas.explorenepal.model.BookmarkList;
import com.rimas.explorenepal.model.BookmarkList_Data;
import com.rimas.explorenepal.model.PostList;
import com.rimas.explorenepal.model.RecommendationList;
import com.rimas.explorenepal.model.SinglePost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> implements LocationListener {

    private Context context;
    private List<PostList> postLists;
    CardView cardView;
    ImageButton btnMap;
    Fragment MapFragment;
    private LocationManager locationManager;
    double distance;
    private double latitude, longitude;

    private List<RecommendationList> recommendationLists;
    private ArrayList<SinglePost> favouriteLists;


    public ExploreAdapter(Context context, List<PostList> postLists) {
        this.context = context;
        this.postLists = postLists;

    }
    public void setPostList(List<PostList> postLists) {
        this.postLists = postLists;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.explore_top_layout, parent,false);
        cardView=view.findViewById(R.id.cardExploree);
        btnMap=view.findViewById(R.id.exploreMap);

        setLocationManager();

        return new ExploreViewHolder(view);


    }

    private void setLocationManager(){

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more Details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

    }




    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        PostList postList=postLists.get(position);
        int id= postList.getId();
        holder.btnBookmark.setTag("R.drawable.ic_bookmark");

        Call<ArrayList<SinglePost>> selectCall= BookmarkApi.getExploreService().getSingleData();
        selectCall.enqueue(new Callback<ArrayList<SinglePost>>() {
            @Override
            public void onResponse(Call<ArrayList<SinglePost>> call, Response<ArrayList<SinglePost>> response) {
                favouriteLists=response.body();

                for(int i=0; i<favouriteLists.size();i++){

                    if (postList.getId()==favouriteLists.get(i).getId()){

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

           holder.name.setText(postList.getName());
           holder.location.setText(postList.getLocation());
           Glide.with(holder.exploreImage.getContext())
                   .load(Uri.parse(postList.getImage()))
                   .placeholder(holder.exploreImage.getDrawable())
                   .into(holder.exploreImage);

        if (ExploreFragment.bookmarkDatabase.bookmarkListDao().isWish(id)==1){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black);

        }
        else {
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);

        }
        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookmarkList bookmarkList= new BookmarkList();
                int id =postList.getId();
                String name = postList.getName();
                String description = postList.getDescription();
                String location = postList.getLocation();
                Double lat= postList.getLat();
                Double longitude= postList.getLong();
                String imageurl= postList.getImage();

                bookmarkList.setId(id);
                bookmarkList.setName(name);
                bookmarkList.setDescription(description);
                bookmarkList.setLocation(location);
                bookmarkList.setLat(lat);
                bookmarkList.setLong(longitude);
                bookmarkList.setImage(imageurl);

                if (ExploreFragment.bookmarkDatabase.bookmarkListDao().isWish(id)==1){
                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);
                    ExploreFragment.bookmarkDatabase.bookmarkListDao().addTobookmarkdata(bookmarkList);

                }
                else {
                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black);
                    ExploreFragment.bookmarkDatabase.bookmarkListDao().delete(bookmarkList);

                }



            }
        });


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

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(holder.btnBookmark.getTag().equals("R.drawable.ic_bookmark")){
                    holder.btnBookmark.setTag("R.drawable.ic_bookmark_black");
                    Toast.makeText(context, "Added to bookmark", Toast.LENGTH_SHORT).show();

                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black);
                    retrofit2.Call<BookmarkList_Data> newCall = RecommendationApi.getExploreService().savePost(postList.getId(),postList.getName(),postList.getLocation()
                            ,postList.getDescription(),postList.getLat(), postList.getLong(), postList.getImage());
                    newCall.enqueue(new Callback<BookmarkList_Data>() {
                        @Override
                        public void onResponse(retrofit2.Call<BookmarkList_Data> call, Response<BookmarkList_Data> response) {
                            Toast.makeText(context, "Success in inserting babe", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(retrofit2.Call<BookmarkList_Data> call, Throwable t) {

//                               Toast.makeText(context, "Oopss. sorry", Toast.LENGTH_SHORT).show();
//                               Log.e("failure", String.valueOf(t.getCause()));

                        }
                    });

                }
                else{
                    holder.btnBookmark.setTag("R.drawable.ic_bookmark");

                    Toast.makeText(context, "Removed from bookmark", Toast.LENGTH_SHORT).show();

                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark);

                    retrofit2.Call<BookmarkList_Data> deleteData= RecommendationApi.getExploreService().deletePost(postList.getId());
                    deleteData.enqueue(new Callback<BookmarkList_Data>() {
                        @Override
                        public void onResponse(retrofit2.Call<BookmarkList_Data> call, Response<BookmarkList_Data> response) {

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


//

        holder.name.setOnClickListener(new View.OnClickListener() {
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
        holder.location.setOnClickListener(new View.OnClickListener() {
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
        holder.exploreImage.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {

//        return postLists.size();
       return postLists == null ? 0 : postLists.size();
    }

    @Override
    public void onLocationChanged(Location location) {

        longitude= location.getLongitude();
        latitude= location.getLatitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

            btnMap= itemView.findViewById(R.id.exploreMap);
            btnBookmark= itemView.findViewById(R.id.exploreBookmark);
        }
    }
}
