package com.rimas.explorenepal.fragments;


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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    public List<BookmarkList> bookmarkLists;
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
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView=v.findViewById(R.id.bookmarkRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager((BookmarkFragment.this.getContext()));
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        bookmarkAdapter= new BookmarkAdapter(getActivity(), bookmarkLists);
        recyclerView.setAdapter(bookmarkAdapter);
        getBookmarkData();

        bookmarkDatabase= Room.databaseBuilder(getContext(), BookmarkDatabase.class,"bookmark_data").allowMainThreadQueries().build();

        return v;
    }

    private void getBookmarkData() {

        List<BookmarkList> bookmarkLists= ExploreFragment.bookmarkDatabase.bookmarkListDao().getBookmarkListData();

        bookmarkAdapter.setPostList(bookmarkLists);
        recyclerView.setAdapter(bookmarkAdapter);






            }


    }

