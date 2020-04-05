package com.rimas.explorenepal.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.fragments.BookmarkFragment;
import com.rimas.explorenepal.fragments.ExploreFragment;
import com.rimas.explorenepal.fragments.MapFragment;
import com.rimas.explorenepal.fragments.ProfileFragment;
import com.rimas.explorenepal.fragments.RecommendationFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Fragment selectedFragment;
    private int REQUEST_CHECK_SETTINGS=11;
    boolean value=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Explore");
        setSupportActionBar(toolbar);
        if (getIntent().hasExtra("Value")){
            value=getIntent().getBooleanExtra("Value", false);
        }
        if (value) {
            selectedFragment = new ExploreFragment();
        }
        else {
            selectedFragment=new BookmarkFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.exploreFragment, selectedFragment).commit();
        createLocationRequest();
        navigation();

    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {


//                Toast.makeText(MainActivity.this, "Gps already open",
//                        Toast.LENGTH_LONG).show();
//                Log.d("location settings",locationSettingsResponse.toString());
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CHECK_SETTINGS){

            if(resultCode==RESULT_OK){

                Toast.makeText(this, "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.d("result ok",data.toString());

            }else if(resultCode==RESULT_CANCELED){

                Toast.makeText(this, "refused to open gps",
                        Toast.LENGTH_SHORT).show();
                // in case user back press or refuses to open gps
                Log.d("result cancelled",data.toString());
            }
        }
    }



    public void navigation(){

        bottomNavigationView= findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {
                    case R.id.id_explore:
                        selectedFragment = new ExploreFragment();
                        toolbar.setTitle("Explore");
                        break;
                    case R.id.id_recommendation:
                        selectedFragment = new RecommendationFragment();
                        toolbar.setTitle("Recommendation");
                        break;
                    case R.id.id_map:
                        selectedFragment = new MapFragment();
                        toolbar.setTitle("Maps");
                        break;
                    case R.id.id_bookmark:
                        selectedFragment = new BookmarkFragment();
                        toolbar.setTitle("Bookmark");
                        break;
                    case R.id.id_profile:
                        selectedFragment = new ProfileFragment();
                        toolbar.setTitle("About us");
                        break;


                    default:
                        Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.exploreFragment, selectedFragment).commit();
                return true;

            }
        });

    }
}

