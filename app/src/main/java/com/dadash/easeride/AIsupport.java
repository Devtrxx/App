package com.dadash.easeride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.os.Build;
import android.webkit.WebResourceRequest;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AIsupport extends AppCompatActivity {
    private WebView webView;
    private ImageButton pubbtn, homebtn,wallbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisupport);
        // Initialize WebView
        webView = findViewById(R.id.webView);

        // Enable JavaScript (optional, depends on the website)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        configureWebView();
        // Load the website
        webView.loadUrl("http://192.168.9.126:3000/pages");

        // Set WebViewClient to handle navigation within the WebView
        webView.setWebViewClient(new WebViewClient());


        pubbtn =findViewById(R.id.btnAdd);
        homebtn =findViewById(R.id.btnAccount);
        wallbtn =findViewById(R.id.btnWallet);


        OpenACtivity();
    }

    private void OpenACtivity(){
        pubbtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent = new Intent(AIsupport.this, publishride.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }
        );

        wallbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(AIsupport.this, Wallet.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );
        homebtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(AIsupport.this, Home.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
        );
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
    private void configureWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable mixed content if your API is served over HTTP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Set a WebViewClient to handle requests and page navigation
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // You can modify headers here if needed
                return super.shouldInterceptRequest(view, request);
            }
        });
    }

}

