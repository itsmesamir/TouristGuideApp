package com.rimas.explorenepal.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rimas.explorenepal.R;
import com.rimas.explorenepal.adapters.ExploreAdapter;
import com.rimas.explorenepal.adapters.PopularAdapter;
import com.rimas.explorenepal.adapters.RecommendationAdapter;
import com.rimas.explorenepal.api.RecommendationApi;
import com.rimas.explorenepal.model.PopularList;
import com.rimas.explorenepal.model.PostList;
import com.rimas.explorenepal.model.RecommendationList;

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
public class RecommendationFragment extends Fragment {

    public ArrayList<RecommendationList> recommendationLists;
    RecyclerView recyclerView;
    RecommendationAdapter recommendationAdapter;


    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialogManager();
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_recommendation, container, false);
        recommendationAdapter= new RecommendationAdapter(getContext(), recommendationLists);
        recyclerView=v.findViewById(R.id.recommendationRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager((RecommendationFragment.this.getContext()));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recommendationAdapter = new RecommendationAdapter(getActivity(),recommendationLists);
        recyclerView.setAdapter(recommendationAdapter);
        getRecommendationData();
        return v;
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
        }, 3000);
    }

    private void getRecommendationData() {

        Call<ArrayList<RecommendationList>> listCall= RecommendationApi.getExploreService().getRecommendationList();

        listCall.enqueue(new Callback<ArrayList<RecommendationList>>() {
            @Override
            public void onResponse(Call<ArrayList<RecommendationList>> call, Response<ArrayList<RecommendationList>> response) {
                recommendationLists= response.body();
                recommendationAdapter.setPostLists(recommendationLists);

            }

            @Override
            public void onFailure(Call<ArrayList<RecommendationList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in response", Toast.LENGTH_SHORT).show();


            }
        });


    }

}
