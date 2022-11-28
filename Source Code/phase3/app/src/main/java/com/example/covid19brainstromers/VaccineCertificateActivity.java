package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VaccineCertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_certificate);
        WebView webView = findViewById(R.id.webVeiw1);
        webView.loadUrl("https://www.covidbrainstromers.website/brainstromers/admin/show_certificate.php?username="+GlobalVariables.username);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}