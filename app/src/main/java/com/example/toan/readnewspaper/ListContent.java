package com.example.toan.readnewspaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toan.readnewspaper.empty.CustomList;
import com.example.toan.readnewspaper.webview.ContentExecute;

/**
 * Created by toan on 15/12/2016.
 */

public class ListContent extends Activity {
    ListView list;
    protected String[] web = new String[30];
    protected Integer[] imageId = new Integer[30];
    protected String[] linkRead = new String[30];

    protected String type = "";// type = dantri_type

    //protected final String DAN_TRI_TYPE = "dan_tri";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);
        InitValue();
        CustomList adapter = new
                CustomList(ListContent.this, web, imageId,linkRead);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ListContent.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListContent.this, ContentExecute.class);

                Bundle myBundle = new Bundle();
                myBundle.putString("read_link",linkRead[position]);
                myBundle.putString(type,web[position]);
                Log.d("myLog",linkRead[position]);
                intent.putExtras(myBundle);

                startActivity(intent);

            }
        });

        registerForContextMenu(list);
    }
    protected void InitValue(){

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }
}
