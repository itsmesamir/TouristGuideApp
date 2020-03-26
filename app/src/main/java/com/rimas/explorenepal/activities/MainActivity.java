package com.rimas.explorenepal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Explore");
        setSupportActionBar(toolbar);
        selectedFragment=new ExploreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.exploreFragment, selectedFragment).commit();
        navigation();

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

