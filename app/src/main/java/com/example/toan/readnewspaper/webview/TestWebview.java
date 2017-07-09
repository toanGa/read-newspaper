package com.example.toan.readnewspaper.webview;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.file.SoftFile;

/**
 * Created by toan on 09/12/2016.
 */

public class TestWebview extends Activity {
    WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_webview);
        webView = (WebView)findViewById(R.id.webview_test);

        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default;
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type_webview");
        String readOffline = bundle.getString("read_offline");

        Log.d("myLog","type trong testwebview" + type);
        Log.d("myLog","readOffline trong testwebview" + readOffline);
        SoftFile softFile = null;
        if(type == "dan_tri"){
            softFile = new SoftFile(TestWebview.this,"dan_tri");
        }else if(type == "vn_express"){
            softFile = new SoftFile(TestWebview.this,"vn_express");
        }
        else
            softFile = new SoftFile(TestWebview.this,readOffline);
        //if(!isNetworkAvailable()){
            webView.loadDataWithBaseURL("",softFile.GetContentFile(),"text/html", "UTF-8", "");
        //}
        /*
        setContentView(R.layout.test_webview);
        webView = (WebView)findViewById(R.id.webview_test);
        //WebView webView = new WebView( this );
        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }else
            webView.loadUrl( "http://www.facebook.com" );
            */

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
