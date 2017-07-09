package com.example.toan.readnewspaper.jsouptest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.toan.readnewspaper.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by toan on 14/12/2016.
 */
public class JsoupVnExpress extends Activity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.mytestjsoup);

        new Soup().execute("http://vnexpress.net/tin-tuc/thoi-su/canh-sat-giao-thong-da-nang-len-facebook-doi-thoai-voi-dan-3512479.html");
    }
    String detail = "";
    class Soup extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            String link = params[0];
            try{
                Document doc = Jsoup.connect(link).get();
                Elements title = doc.select("div.title_news h1");
                Elements date = doc.select("div.fck_detail");
                Elements description = doc.select("block_col_480 h3");
                doc.select("table").remove();

                Elements main = doc.select("div.ovh.content ");
                detail += "<h2 style = \" color: red \">" + title.text()
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + description.text() + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main.toString() + "</font>";
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("myLog","parse xong..................");
            Log.d("myLog",detail);
            textView.setText("da xong");
        }
    }
}
