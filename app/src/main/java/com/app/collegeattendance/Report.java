package com.app.collegeattendance;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Report extends AppCompatActivity {

    WebView wbDAta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        wbDAta=findViewById(R.id.wbDAta);

        wbDAta.getSettings().setJavaScriptEnabled(true);
        wbDAta.getSettings().setPluginState(WebSettings.PluginState.ON);
        wbDAta.loadUrl("http://trivediservices.com/attendance_admin/mobile_report.php");

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
