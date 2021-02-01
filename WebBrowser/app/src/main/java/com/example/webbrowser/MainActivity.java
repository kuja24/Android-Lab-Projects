package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ImageButton searchBtn,backBtn;
    private EditText urlText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_browser);
        searchBtn = findViewById(R.id.search_btn);
        backBtn = findViewById(R.id.back_btn);
        urlText = findViewById(R.id.url_txt);

        webView.loadUrl("https://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.canGoBack();

        webView.setWebViewClient(new myWebViewClient());
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String url = urlText.getText().toString();
                 webView.loadUrl(url);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
            }
        });
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}