package com.example.toan.readnewspaper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.toan.readnewspaper.rss.RSSItem;
import com.example.toan.readnewspaper.rss.RssParser;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toan on 23/11/2016.
 */

public class ContextPaper extends FragmentActivity {
    protected List<RSSItem> items;// lưu list các bài báo
    protected RssParser rssParser = new RssParser();// để parse link thành list các bài báo
    protected ListView listView;// show list các bài báo

    protected String linkToRead = "";//link lấy từ bundle để parse thành các bài báo


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoader(this);//khởi tạo đặc tính ảnh trước khi load vào giao diện

        items = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);

        new ContextPaper.GetListItem(this).execute();
        registerForContextMenu(listView);

    }

    /**
     *  khởi tạo context menu để lưu bài viết
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        RSSItem item1 = items.get(info.position);// lấy ra thông tin của bài báo được chọn
        Log.d("myLog",item1.get_link() + "\n" + item1.get_title() + "\n" + item1.get_description() + "\n" + item1.get_img());

        Log.d("myLog","da kich context menu");
        switch (item.getItemId()){
            case R.id.save_bai_viet:
                Log.d("myLog","Luu bai viet");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
    public ContextPaper(String nameNewspaper){
        this.linkToRead = nameNewspaper;
    }
     */

    public class GetListItem extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        Context context;

        GetListItem(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            items = rssParser.getRSSFeedItems(linkToRead);

            Log.d("rss", items.size() + "");
            Log.d("rss", items.get(0).get_link() + "");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            AppAdapter adapter = new AppAdapter(context, items);
            listView.setAdapter(adapter);
            dialog.dismiss();
        }

    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
