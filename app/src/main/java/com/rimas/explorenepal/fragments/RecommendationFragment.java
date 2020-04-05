package com.rimas.explorenepal.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.adapters.RecommendationAdapter;
import com.rimas.explorenepal.api.RecommendationApi;
import com.rimas.explorenepal.model.RecommendationList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {

    public ArrayList<RecommendationList> recommendationLists;
    RecyclerView recyclerView;
    RecommendationAdapter recommendationAdapter;
    private int REQUEST_CHECK_SETTINGS=2;


    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialogManager();
        // Inflate the layout for this fragment
        createLocationRequest();
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

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this.getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        task.addOnSuccessListener(this.getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

//                Toast.makeText(MapFragment.this.getContext(), "Gps already open",
//                        Toast.LENGTH_LONG).show();
//                Log.d("location settings",locationSettingsResponse.toString());
            }
        });

        task.addOnFailureListener(this.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        startIntentSenderForResult(resolvable.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CHECK_SETTINGS){

            if(resultCode==RESULT_OK){

                Toast.makeText(this.getContext(), "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.d("result ok",data.toString());

            }else if(resultCode==RESULT_CANCELED){

                Toast.makeText(this.getContext(), "refused to open gps",
                        Toast.LENGTH_SHORT).show();
                createLocationRequest();
                // in case user back press or refuses to open gps
                Log.d("result cancelled",data.toString());
            }
        }
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
