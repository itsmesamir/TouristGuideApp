package com.rimas.explorenepal.fragments;


import android.app.Application;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rimas.explorenepal.Database.BookmarkDatabase;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.adapters.ExploreAdapter;
import com.rimas.explorenepal.adapters.PopularAdapter;
import com.rimas.explorenepal.api.ExploreApi;
import com.rimas.explorenepal.api.PopularApi;
import com.rimas.explorenepal.model.ExplorePost;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    public List<PostList > postList;
    public ArrayList<PopularList> popularList;
    RecyclerView recyclerView, popularRecyclerView;
    PopularAdapter popularAdapter;
    ExploreAdapter exploreAdapter;
    LinearLayoutManager llm;

    public static BookmarkDatabase bookmarkDatabase;

    ImageButton bookmark_button;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_explore, container, false);

        progressDialogManager();
        exploreAdapter= new ExploreAdapter(getContext(), postList);
        recyclerView=v.findViewById(R.id.exploreRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager((ExploreFragment.this.getContext()));
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        exploreAdapter = new ExploreAdapter(getActivity(),postList);
        recyclerView.setAdapter(exploreAdapter);
        getExploreData();

        popularRecyclerView= v.findViewById(R.id.popularRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((ExploreFragment.this.getContext()));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL  );
        popularRecyclerView.setLayoutManager(linearLayoutManager);
        popularAdapter = new PopularAdapter(getActivity(),popularList);
        popularRecyclerView.setAdapter(popularAdapter);
        bookmarkDatabase= Room.databaseBuilder(getContext(), BookmarkDatabase.class,"bookmark_data").allowMainThreadQueries().build();

        getPopularData();

        return  v;


    }


    private void progressDialogManager() {

        ProgressDialog progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                //delayInMillis=4000;
            }
        }, 4000);
    }

    public void getExploreData(){

        Call<List<PostList>> explorePostCall= ExploreApi.getExploreService().getPostList();
        explorePostCall.enqueue(new Callback<List<PostList>>() {
            @Override
            public void onResponse(Call<List<PostList>> call, Response<List<PostList>> response) {
                postList=response.body();
                exploreAdapter.setPostList(postList);
                if (response.isSuccessful())
                    //Log.e("Success", new Gson().toJson(response.body()));
                    Log.e("Success",new Gson().toJson(postList.get(1)));
                else
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
            }

            @Override
            public void onFailure(Call<List<PostList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in response", Toast.LENGTH_SHORT).show();
                Log.e("failure", String.valueOf(t.getCause()));

            }
        });

    }
    public void getPopularData(){
        Call<ArrayList<PopularList>> popularCall= ExploreApi.getExploreService().getPopularList();
        popularCall.enqueue(new Callback<ArrayList<PopularList>>() {
            @Override
            public void onResponse(Call<ArrayList<PopularList>> call, Response<ArrayList<PopularList>> response) {
              popularList=response.body();
              popularAdapter.setPostLists(popularList);
                if (response.isSuccessful())
                    //Log.e("Success", new Gson().toJson(response.body()));
                    Log.e("Success",new Gson().toJson(popularList.get(1)));
                else
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));

            }

            @Override
            public void onFailure(Call<ArrayList<PopularList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in respe", Toast.LENGTH_SHORT).show();
                Log.e("failureee", String.valueOf(t.getCause()));

            }
        });
    }



}
