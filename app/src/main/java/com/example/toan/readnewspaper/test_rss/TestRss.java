package com.example.toan.readnewspaper.test_rss;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.toan.readnewspaper.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by toan on 06/12/2016.
 */

public class TestRss extends Activity {
    // Main GUI - A NEWS application based on National Public Radio RSS material
    ArrayAdapter<String> adapterMainSubjects;
    ListView myMainListView;
    Context context;
    SingleItem selectedNewsItem;

    String [][] myUrlCaptionMenu = {
            {"http://www.npr.org/rss/rss.php?id=1001", "Top Stories"},
            {"http://www.npr.org/rss/rss.php?id=1003", "U.S. News"},
            {"http://www.npr.org/rss/rss.php?id=1004", "World News"},
            {"http://www.npr.org/rss/rss.php?id=1006", "Business"},
            {"http://www.npr.org/rss/rss.php?id=1007", "Health & Science"},
            {"http://www.npr.org/rss/rss.php?id=1008", "Arts & Entertainment"},
            {"http://www.npr.org/rss/rss.php?id=1012", "Politics & Society"},
            {"http://www.npr.org/rss/rss.php?id=1021", "People & Places"},
            {"http://www.npr.org/rss/rss.php?id=1057", "Opinion"}
    };
    //define convenient URL and CAPTIONs arrays
    String [] myUrlCaption = new String[myUrlCaptionMenu.length];
    String [] myUrlAddress = new String[myUrlCaptionMenu.length];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_rss);
        for (int i=0; i<myUrlCaptionMenu.length; i++) {
            myUrlAddress[i] = myUrlCaptionMenu[i][0];
            myUrlCaption[i] = myUrlCaptionMenu[i][1];
        }

        context = getApplicationContext();
        this.setTitle("NPR Headline News\n" + niceDate() );
        myMainListView = (ListView)this.findViewById(R.id.myListView);
        myMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v,
                                    int _index, long _id) {
                String urlAddress = myUrlAddress[_index];
                String urlCaption = myUrlCaption[_index];
//create an Intent to talk to activity: ShowHeadlines
                Intent callShowHeadlines = new Intent( TestRss.this,
                        ShowHeadlines.class);
                //prepare a Bundle and add the input arguments: url & caption
                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress);
                myData.putString("urlCaption", urlCaption);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });

        // fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, //android's default
        myUrlCaption);
        myMainListView.setAdapter(adapterMainSubjects);
    }

    // method returns a value such as "Monday Apr 7, 2014"
    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy ", Locale.US);
        return sdf.format(new Date() );
    }
}
