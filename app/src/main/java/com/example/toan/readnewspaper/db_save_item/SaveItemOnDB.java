package com.example.toan.readnewspaper.db_save_item;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.toan.readnewspaper.database.NewspaperHelper;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.rss.RSSItem;
import com.example.toan.readnewspaper.test.SaveImageToInternal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * Created by toan on 16/12/2016.
 */

public class SaveItemOnDB  extends AsyncTask<Void,Void,Void>{

    //int x[] = new int[1000000000];
    ItemOnDB itemOnDB;
    Context context;
    RSSItem rssItem;
    ProgressDialog dialog;

    NewspaperHelper helper;

    String htmlLink;
    String imageLink;

    String detail = "";
    int checkFile = 0;
    public SaveItemOnDB(Context context,RSSItem rssItem){
        itemOnDB = new ItemOnDB(null,null,null);
        this.context = context;
        this.rssItem = rssItem;
        itemOnDB.title = rssItem.get_title();
        htmlLink = rssItem.get_link();
        imageLink = rssItem.get_img();

        helper = new NewspaperHelper(context);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Đang lưu bài viết...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(rssItem.get_link()).get();

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


            String filename = "dantri0" ;
            String imageFileName = "dantri0.jpg";

            //filename = filename + 0;
            File file = new File(context.getFilesDir() + "/" + filename);

            while (file.exists()){
                checkFile ++;
                filename = "dantri" + checkFile;
                imageFileName = "dantri" + checkFile + ".jpg";
                Log.d("myLog","file dang check:" + filename);
                file = new File(context.getFilesDir() + "/" + filename);
            }
            /////
            ////
            ////
            SoftFile softFile = new SoftFile(context,filename );
            softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
                    + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                    + detail);
            Log.d("myLog","đã lưu vào" + file.getName());
            Log.d("myLog","test filename:" + filename);
            /**
             *
             *
             */
            Log.d("myLog","file test:" + filename+ "\n imagefiletest: " + imageFileName);
            new SaveImageToInternal(imageLink,context,imageFileName);
            itemOnDB.htmlFileName = filename;
            itemOnDB.imageFileName = imageFileName;

            /**
             *
             *
             */
            helper.SaveItemOnDB(itemOnDB);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Log.d("myLog",detail);
        dialog.dismiss();

    }
}
