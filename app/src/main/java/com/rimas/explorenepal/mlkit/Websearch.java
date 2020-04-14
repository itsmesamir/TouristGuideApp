package com.rimas.explorenepal.mlkit;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rimas.explorenepal.R;

public class Websearch extends AppCompatActivity {

    WebView webView;
    String Landmark_name;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_websearch);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Landmark Details");
        setSupportActionBar(toolbar);

        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        if(getIntent().hasExtra("Landmark_name")){

            Landmark_name= getIntent().getStringExtra("Landmark_name");

            webView.loadUrl("https://en.wikipedia.org/wiki/"+Landmark_name);



        }
    }
}
