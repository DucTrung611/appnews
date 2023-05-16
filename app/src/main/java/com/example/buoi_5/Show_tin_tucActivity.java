package com.example.buoi_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Show_tin_tucActivity extends AppCompatActivity {
    WebView wbv_tin_tuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tin_tuc);

        wbv_tin_tuc = findViewById(R.id.wbv_tin_tuc);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String link = bundle.getString("link");
        Log.e("---------------------" , link);

        wbv_tin_tuc.getSettings().setLoadsImagesAutomatically(true);
        wbv_tin_tuc.getSettings().setJavaScriptEnabled(true);
        wbv_tin_tuc.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wbv_tin_tuc.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
        wbv_tin_tuc.loadUrl(link);
    }
}