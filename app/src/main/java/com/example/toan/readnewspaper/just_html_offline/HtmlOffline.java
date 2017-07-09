package com.example.toan.readnewspaper.just_html_offline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.toan.readnewspaper.NewActivity;
import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.database.NewspaperHelper;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.test.SaveImageToInternal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by toan on 18/12/2016.
 */

public class HtmlOffline extends Activity {
    WebView webView;
    String link, title, imageLink, date;
    String detail = "";
    String type;
    String htmlSource = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        webView = (WebView)findViewById(R.id.desc);
        Bundle bundle = getIntent().getExtras();

        link = bundle.getString("link");
        type = bundle.getString("exe_type");
        title = bundle.getString("exe_title");
        imageLink = bundle.getString("exe_imagelink");

        Log.d("myLog","link trong thang NewActivity:" + link);
        //Log.d("myLog","type trong thang NewActivity:" + type);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);

        //new GetData().execute();

    }

    class GetData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(HtmlOffline.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();

        }

        /**
         *  lay noi dung html file luu lai
         *  luu anh nhu dan tri
         *  luu vao co so du lieu
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(link);
                HttpResponse response = client.execute(request);

                //StringBuffer html = "";
                //String html = "";
                InputStream in = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder str = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    str.append(line);
                }
                in.close();
                htmlSource = str.toString();

                ///////////////////////////////////
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            webView.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + htmlSource, "text/html", "UTF-8", "");
            Log.d("myLog",htmlSource);
            dialog.dismiss();

            SoftFile softFile = new SoftFile(HtmlOffline.this,type);
            softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
                    + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                    + detail);

            Log.d("myLog","da luu vao " + type);

            new SaveImageToInternal(imageLink,HtmlOffline.this,type + ".jpg");

            ItemOnDB itemOnDB = new ItemOnDB(title,type,type + ".jpg");

            NewspaperHelper helper = new NewspaperHelper(HtmlOffline.this);
            helper.SaveItemOnDB(itemOnDB);

            Log.d("mylog","tu dong luu " + itemOnDB.title);
        }

    }
}
