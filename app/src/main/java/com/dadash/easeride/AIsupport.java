package com.dadash.easeride;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AIsupport extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisupport);
        // Initialize WebView
        webView = findViewById(R.id.webView);

        // Enable JavaScript (optional, depends on the website)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the website
        webView.loadUrl("http://192.168.50.126:5000/");

        // Set WebViewClient to handle navigation within the WebView
        webView.setWebViewClient(new WebViewClient());
    }

    // Handle back button press to navigate within WebView history
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
