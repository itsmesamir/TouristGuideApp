package com.rimas.explorenepal.fragments;


import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    public ArrayList<PostList > postList;
    public ArrayList<PopularList> popularList;
    RecyclerView recyclerView, popularRecyclerView;
    PopularAdapter popularAdapter;
    ExploreAdapter exploreAdapter;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_explore, container, false);
        //exploreAdapter= new ExploreAdapter();
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
        getPopularData();

        return v;


    }

    public void getExploreData(){

        Call<ArrayList<PostList>> explorePostCall= ExploreApi.getExploreService().getPostList();
        explorePostCall.enqueue(new Callback<ArrayList<PostList>>() {
            @Override
            public void onResponse(Call<ArrayList<PostList>> call, Response<ArrayList<PostList>> response) {
                postList=response.body();
                exploreAdapter.setPostList(postList);
                if (response.isSuccessful())
                    //Log.e("Success", new Gson().toJson(response.body()));
                    Log.e("Success",new Gson().toJson(postList.get(1)));
                else
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));
            }

            @Override
            public void onFailure(Call<ArrayList<PostList>> call, Throwable t) {
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
