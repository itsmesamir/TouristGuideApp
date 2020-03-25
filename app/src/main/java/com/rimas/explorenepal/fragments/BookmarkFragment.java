package com.rimas.explorenepal.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rimas.explorenepal.Database.BookmarkDatabase;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.adapters.BookmarkAdapter;
import com.rimas.explorenepal.adapters.ExploreAdapter;
import com.rimas.explorenepal.api.BookmarkApi;
import com.rimas.explorenepal.model.BookmarkList;
import com.rimas.explorenepal.model.BookmarkList_Data;
import com.rimas.explorenepal.model.FavouriteList;
import com.rimas.explorenepal.model.PopularList;

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
public class BookmarkFragment extends Fragment {

    public ArrayList<FavouriteList> bookmarkLists;
    RecyclerView recyclerView;
    BookmarkAdapter bookmarkAdapter;
    public static BookmarkDatabase bookmarkDatabase;

    ImageButton bookmark_button;


    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialogManager();
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView=v.findViewById(R.id.bookmarkRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager((BookmarkFragment.this.getContext()));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        bookmarkAdapter= new BookmarkAdapter(getActivity(), bookmarkLists);
        recyclerView.setAdapter(bookmarkAdapter);
        getBookmarkData();


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
        }, 4000);
    }

    private void getBookmarkData() {


        Call<ArrayList<FavouriteList>> newCall= BookmarkApi.getExploreService().getData();
        newCall.enqueue(new Callback<ArrayList<FavouriteList>>() {
            @Override
            public void onResponse(Call<ArrayList<FavouriteList>> call, Response<ArrayList<FavouriteList>> response) {
                bookmarkLists=response.body();
                bookmarkAdapter.setPostList(bookmarkLists);
                if (response.isSuccessful())
                    //Log.e("Success", new Gson().toJson(response.body()));
                    Log.e("Success",new Gson().toJson(bookmarkLists.get(1)));
                else
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));

            }

            @Override
            public void onFailure(Call<ArrayList<FavouriteList>> call, Throwable t) {

                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_SHORT).show();

            }
        });







            }


    }

