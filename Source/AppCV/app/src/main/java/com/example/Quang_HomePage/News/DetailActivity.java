package com.example.Quang_HomePage.News;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationcv.R;


public class DetailActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        webView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

       // webView.getSettings().setJavaScriptEnabled(true);   // ngon hon, nhung lag hon
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

    }
}