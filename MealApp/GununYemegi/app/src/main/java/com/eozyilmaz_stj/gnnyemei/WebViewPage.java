package com.eozyilmaz_stj.gnnyemei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.URLEncoder;

public class WebViewPage extends AppCompatActivity{
    WebView webView;
    String url;
    String txtIdValue;
    String addUrlValue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent myIntent = getIntent();
        txtIdValue = myIntent.getStringExtra("txtId");
        if (txtIdValue.equals("Soup")) {
            addUrlValue = URLEncoder.encode(MainScrollingActivity.txtSoup.getText().toString());
            url = "https://www.google.com/search?q=" + addUrlValue;
        }
        else if (txtIdValue.equals("MainCourse")){
            addUrlValue = URLEncoder.encode(MainScrollingActivity.txtMainCourse.getText().toString());
            url = "https://www.google.com/search?q=" + addUrlValue;
        }
        else if (txtIdValue.equals("AlternativeMeal")){
            addUrlValue = URLEncoder.encode(MainScrollingActivity.txtAlternativeMeal.getText().toString());
            url = "https://www.google.com/search?q=" + addUrlValue;
        }
        else if (txtIdValue.equals("SupplementalMeal")){
            addUrlValue = URLEncoder.encode(MainScrollingActivity.txtSupplementalMeal.getText().toString());
            url = "https://www.google.com/search?q=" + addUrlValue;
        }
        else if (txtIdValue.equals("Dessert")){
            addUrlValue = URLEncoder.encode(MainScrollingActivity.txtDessert.getText().toString());
            url = "https://www.google.com/search?q=" + addUrlValue;
        }

        webView = (WebView) findViewById(R.id.WebPage);
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}


