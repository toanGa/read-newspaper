package com.example.toan.readnewspaper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.toan.readnewspaper.database.NewspaperHelper;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.test.SaveImageToInternal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by luongnguyen on 3/23/16.
 */
public class NewActivity extends Activity {

    WebView webview;
    String link, title, imageLink, date;
    String detail = "";
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_layout);

        webview = (WebView) findViewById(R.id.desc);
        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");
        type = bundle.getString("exe_type");
        title = bundle.getString("exe_title");
        imageLink = bundle.getString("exe_imagelink");

        Log.d("myLog","link trong thang NewActivity:" + link);
        //Log.d("myLog","type trong thang NewActivity:" + type);

        WebSettings webSettings = webview.getSettings();
        webSettings.setSupportZoom(true);

        new GetData().execute();



    }

    public class GetData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(NewActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(link)
                        .get();

                Elements title = doc.select("div.ovh.detail_w h1");
                Elements date = doc.select("div.publishdate");
                Elements description = doc.select("div.ovh.detail_w h2");
                doc.select("table").remove();
                Elements main = doc.select("div.ovh.content ");
                detail += "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";

                //NewActivity.this.title = title.text();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            webview.loadDataWithBaseURL(
                    "",
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + detail, "text/html", "UTF-8", "");
            Log.d("myLog",detail);
            dialog.dismiss();

            SoftFile softFile = new SoftFile(NewActivity.this,type);
            softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
                    + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                    + detail);

            Log.d("myLog","da luu vao " + type);

            new SaveImageToInternal(imageLink,NewActivity.this,type + ".jpg");

            ItemOnDB itemOnDB = new ItemOnDB(title,type,type + ".jpg");
            NewspaperHelper helper = new NewspaperHelper(NewActivity.this);

            helper.SaveItemOnDB(itemOnDB);

            Log.d("mylog","tu dong luu " + itemOnDB.title);
        }

    }
}
