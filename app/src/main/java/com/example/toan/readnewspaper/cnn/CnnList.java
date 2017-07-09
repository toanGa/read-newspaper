package com.example.toan.readnewspaper.cnn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.empty.CustomList;

/**
 * Created by toan on 19/12/2016.
 */

public class CnnList extends Activity {
    ListView list;

    String[] web = {
            "Top Stories",
            "World",
            "Africa",
            "Americas",
            "Asia",
            "Europe",
            "Middle East",///
            "U.S.",
            "Money",
            "Technology",
            "Science & Space",
            "Entertainment",
            "World Sport",
            "Football",
            "Golf",
            "Motorsport",
            "Tennis",
            "Travel",
            "Video",
            "Most Recent",
            "Business ",
            "Politics",
            "Technology",
            "Health"
    } ;
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,/////
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image4,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image4

    };
    String[] linkRead = {
            "http://rss.cnn.com/rss/edition.rss",
            "http://rss.cnn.com/rss/edition_world.rss",
            "http://rss.cnn.com/rss/edition_africa.rss",
            "http://rss.cnn.com/rss/edition_americas.rss",
            "http://rss.cnn.com/rss/edition_asia.rss",
            "http://rss.cnn.com/rss/edition_europe.rss",
            "http://rss.cnn.com/rss/edition_meast.rss",
            "http://rss.cnn.com/rss/edition_us.rss",
            "http://rss.cnn.com/rss/money_news_international.rss",
            "http://rss.cnn.com/rss/edition_technology.rss",
            "http://rss.cnn.com/rss/edition_space.rss",
            "http://rss.cnn.com/rss/edition_entertainment.rss",
            "http://rss.cnn.com/rss/edition_sport.rss",
            "http://rss.cnn.com/rss/edition_football.rss",
            "http://rss.cnn.com/rss/edition_golf.rss",
            "http://rss.cnn.com/rss/edition_motorsport.rss",
            "http://rss.cnn.com/rss/edition_tennis.rss",
            "http://rss.cnn.com/rss/edition_travel.rss",
            "http://rss.cnn.com/rss/cnn_freevideo.rss",
            "http://rss.cnn.com/rss/cnn_latest.rss",
            "http://rss.cnn.com/rss/money_latest.rss",
            "http://rss.cnn.com/rss/cnn_allpolitics.rss",
            "http://rss.cnn.com/rss/cnn_tech.rss",
            "http://rss.cnn.com/rss/cnn_health.rss"
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);

        CustomList adapter = new
                CustomList(CnnList.this, web, imageId,linkRead);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CnnList.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                //DanTriChuyenMuc ddddd = new DanTriChuyenMuc(linkRead[position]);
                Intent intent = new Intent(CnnList.this, CnnExecute.class);

                Bundle myBundle = new Bundle();
                myBundle.putString("read_link",linkRead[position]);
                myBundle.putString("cnn_type",web[position]);
                Log.d("myLog",linkRead[position]);
                intent.putExtras(myBundle);

                startActivity(intent);

            }
        });
        //registerForContextMenu(list);
    }
}
