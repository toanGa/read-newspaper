package com.example.toan.readnewspaper.webview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.toan.readnewspaper.ContextPaper;
import com.example.toan.readnewspaper.NewActivity;
import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.rss.RSSItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * Created by toan on 15/12/2016.
 */

public class ContentExecute extends ContextPaper {
    protected String exe_type = "";// exe_type = "dan_tri"
    private int checkFile  = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        linkToRead = bundle.getString("read_link");
        SetExeType(exe_type);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContentExecute.this, NewActivity.class);
                //intent.putExtra("link", items.get(position).get_link());
                Bundle myBundle = new Bundle();
                myBundle.putString("link", items.get(position).get_link());
                myBundle.putString("exe_type",exe_type);
                Log.d("myLog",items.get(position).get_img());
                Log.d("myLog",items.get(position).get_title());
                Log.d("myLog",items.get(position).get_description());

                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });

    }

    protected void SetExeType(String exe_type){
        this.exe_type = exe_type;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("myLog","test thang con ok");
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final RSSItem item1 = items.get(info.position);
        Log.d("myLog",item1.get_link() + "\n" + item1.get_title());


        Log.d("myLog","da kich context menu");
        switch (item.getItemId()){
            case R.id.save_bai_viet:
                Log.d("myLog","Luu bai viet o thang con");
                new ContentExecute.SaveData().execute(item1.get_link());

                return true;
        }
        return true;
        //return super.onContextItemSelected(item);
    }

    private String detail = "";
    private class SaveData extends AsyncTask<String,Void,Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ContentExecute.this);
            dialog.setMessage("Đang lưu bài viết...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0])
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


                String filename = "dantri" ;

                File file = new File(getFilesDir() + "/" + filename + 0);

                while (file.exists()){
                    checkFile ++;
                    filename = "dantri" + checkFile;
                    Log.d("myLog","file dang check:" + filename);
                    file = new File(getFilesDir() + "/" + filename);
                }
                /////
                ////
                ////
                SoftFile softFile = new SoftFile(ContentExecute.this,filename );
                softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                        + detail);
                Log.d("myLog","đã lưu vào" + file.getName());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

        }
    }
}
