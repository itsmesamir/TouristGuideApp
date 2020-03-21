package com.rimas.explorenepal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rimas.explorenepal.R;
import com.rimas.explorenepal.fragments.MapFragment;

public class Details extends AppCompatActivity {

    ImageView imagee;
    TextView name, description, location;
    ImageButton bookmark, map;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imagee= findViewById(R.id.detailsImage);
        name= findViewById(R.id.detailsName);
        description= findViewById(R.id.detailsDescription);
        location= findViewById(R.id. detailsLocation);

       getAdapterIntent();
    }

    private void getAdapterIntent(){
        if(getIntent().hasExtra("imageName") && getIntent().hasExtra("imageDescription") && getIntent().hasExtra("imageLocation") &&
        getIntent().hasExtra("image")){

            String imageName= getIntent().getStringExtra("imageName");
            String imageDescription= getIntent().getStringExtra("imageDescription");
            String imageLocation= getIntent().getStringExtra("imageLocation");
            String image= getIntent().getStringExtra("image");

            setData(imageName, imageDescription, imageLocation, image);
        }

    }

    private void setData(String imageName, String imageDescription, String imageLocation, String image) {
        name.setText(imageName);
        description.setText(imageDescription);
        location.setText(imageLocation);

        Glide.with(this)
                .load(Uri.parse(image))
               .into(imagee);

    }
}
