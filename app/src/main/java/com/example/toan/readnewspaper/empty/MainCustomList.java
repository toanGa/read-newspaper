package com.example.toan.readnewspaper.empty;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.toan.readnewspaper.R;

/**
 * Created by toan on 04/12/2016.
 */

public class MainCustomList extends Activity {
    ListView list;
    String[] web = {
            "Google Plus",
            "Twitter",
            "Drupal",
            "Bing",
            "Itunes",
            "Wordpress",
            "Windows"
    } ;
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7

    };
    String[] linkRead = {
            "Google Plus",
            "Twitter",
            "Drupal",
            "Bing",
            "Itunes",
            "Wordpress",
            "Windows"
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);

        CustomList adapter = new
                CustomList(MainCustomList.this, web, imageId,linkRead);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainCustomList.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }
}
