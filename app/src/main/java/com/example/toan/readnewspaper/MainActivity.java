package com.example.toan.readnewspaper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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




public class MainActivity extends FragmentActivity {

	List<RSSItem> items;
	RssParser rssParser = new RssParser();
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initImageLoader(this);

		items = new ArrayList<>();
		listView = (ListView) findViewById(R.id.listview);



		new GetListItem(this).execute();


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(MainActivity.this, NewActivity.class);
				intent.putExtra("link", items.get(i).get_link());
				startActivity(intent);
			}
		});

	}

	public class GetListItem extends AsyncTask<Void, Void, Void> {

		Context context;
		ProgressDialog pd;

		GetListItem(Context context) {
			this.context = context;
		}

		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... params) {
			items = rssParser.getRSSFeedItems("http://dantri.com.vn/trangchu.rss");

			Log.d("rss", items.size() + "");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			AppAdapter adapter = new AppAdapter(context, items);
			listView.setAdapter(adapter);

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