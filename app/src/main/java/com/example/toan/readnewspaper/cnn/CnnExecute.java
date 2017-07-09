package com.example.toan.readnewspaper.cnn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.toan.readnewspaper.ContextPaper;
import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.database.NewspaperHelper;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.just_html_offline.HtmlOnline;
import com.example.toan.readnewspaper.rss.RSSItem;
import com.example.toan.readnewspaper.test.SaveImageToInternal;
import com.example.toan.readnewspaper.vnexpress.VnExpressExecute;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by toan on 19/12/2016.
 */

public class CnnExecute extends ContextPaper{
    int checkFile  = 0;
    String htmlSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        linkToRead = bundle.getString("read_link");

        Log.d("myLog","link trong cnn " + linkToRead);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CnnExecute.this, HtmlOnline.class);
                //intent.putExtra("link", items.get(position).get_link());
                Bundle myBundle = new Bundle();
                myBundle.putString("link", items.get(position).get_link());
                myBundle.putString("exe_type","cnn");
                myBundle.putString("exe_imagelink",items.get(position).get_img());
                myBundle.putString("exe_title",items.get(position).get_title());

                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("myLog","test thang con ok");
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        RSSItem item1 = items.get(info.position);
        Log.d("myLog",item1.get_link() + "\n" + item1.get_title());


        Log.d("myLog","da kich context menu");
        switch (item.getItemId()){
            case R.id.save_bai_viet:
                Log.d("myLog","Luu bai viet o thang con");
                new SaveData().execute(item1.get_link(),item1.get_img(),item1.get_title());
                return true;
        }
        return true;
    }

    private class SaveData extends AsyncTask<String,Void,Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CnnExecute.this);
            dialog.setMessage("Đang lưu bài viết...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(params[0]);
                HttpResponse response = client.execute(request);

                InputStream in = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder str = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null)
                {
                    str.append(line);
                }
                in.close();
                htmlSource = str.toString();

                String filename = "cnn0" ;
                String imageFileName = "cnn0.jpg";

                File file = new File(getFilesDir() + "/" + filename);
//                while (file.exists()){
//                    checkFile ++;
//                    filename = "vnexpress" + checkFile;
//                    Log.d("myLog","file dang check:" + filename);
//                    file = new File(getFilesDir() + "/" + filename);
//                }
                while (file.exists()){
                    checkFile ++;
                    filename = "cnn" + checkFile;
                    imageFileName = "imgcnn" + checkFile + ".jpg";
                    Log.d("myLog","file dang check:" + filename);
                    file = new File(getFilesDir() + "/" + filename);
                }

//                SoftFile softFile = new SoftFile(VnExpressExecute.this,filename );
//                softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
//                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
//                        + htmlSource);
//                Log.d("myLog","đã lưu vào" + file.getName());
//
//                new SaveImageToInternal(params[1],VnExpressExecute.this,filename + ".jpg");
//
//                ItemOnDB itemOnDB = new ItemOnDB(params[2],filename,filename + ".jpg");
//
//                NewspaperHelper helper = new NewspaperHelper(VnExpressExecute.this);
//                helper.SaveItemOnDB(itemOnDB);


                SoftFile softFile = new SoftFile(CnnExecute.this,filename );
                softFile.SetContentFile("<style>img{display: inline;height: auto;max-width: 100%;}"
                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                        + htmlSource);
                Log.d("myLog","đã lưu vào" + file.getName());
                Log.d("myLog","test filename:" + filename);

                Log.d("myLog","file test:" + filename+ "\n imagefiletest: " + imageFileName);
                new SaveImageToInternal(params[1],CnnExecute.this,imageFileName);
                ItemOnDB itemOnDB = new ItemOnDB(params[2],filename,imageFileName);
                /**
                 *
                 *
                 */
                NewspaperHelper helper = new NewspaperHelper(CnnExecute.this);
                helper.SaveItemOnDB(itemOnDB);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("myLog","bat dau luu file");
            dialog.dismiss();
        }
    }

}
