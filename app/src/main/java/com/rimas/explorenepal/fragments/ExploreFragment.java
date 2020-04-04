package com.rimas.explorenepal.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.rimas.explorenepal.Database.BookmarkDatabase;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.adapters.ExploreAdapter;
import com.rimas.explorenepal.adapters.PopularAdapter;
import com.rimas.explorenepal.api.ExploreApi;
import com.rimas.explorenepal.mlkit.BarcodeDetection;
import com.rimas.explorenepal.mlkit.LandmarkDetection;
import com.rimas.explorenepal.mlkit.TextDetection;
import com.rimas.explorenepal.mlkit.TextTranslation;
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
    FloatingActionButton fab;
    Button btnTextDetection, btnTextTranslation, btnBarcodeDetection, btnLandmarkDetection;

    public static BookmarkDatabase bookmarkDatabase;

    ImageButton bookmark_button;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_explore, container, false);
        fab=v.findViewById(R.id.fab);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detector();
            }
        });

        return  v;


    }
    private void detector() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExploreFragment.this.getContext());
        View mView = getLayoutInflater().inflate(R.layout.scannerdialog, null);

        btnTextDetection= mView.findViewById(R.id.btnTextDetection);
        btnTextTranslation= mView.findViewById(R.id.btnTextTranslation);
        btnBarcodeDetection=mView.findViewById(R.id.btnBarcodeDetection);
        btnLandmarkDetection= mView.findViewById(R.id.btnLandmarkDetection);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnTextDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent= new Intent(ExploreFragment.this.getContext(), TextDetection.class);
                startActivity(intent);

            }
        });
        btnTextTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent= new Intent(ExploreFragment.this.getContext(), TextTranslation.class);
                startActivity(intent);

            }
        });
        btnBarcodeDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent= new Intent(ExploreFragment.this.getContext(), BarcodeDetection.class);
                startActivity(intent);

            }
        });
        btnLandmarkDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent= new Intent(ExploreFragment.this.getContext(), LandmarkDetection.class);
                startActivity(intent);

            }
        });




//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });

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
