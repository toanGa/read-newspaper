package com.example.toan.readnewspaper.offline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toan.readnewspaper.Main;
import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.webview.TestWebview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toan on 10/12/2016.
 */

public class MainOffline extends Activity {
    ListView listView;
    ArrayList<String> listOffline;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_offline);
        listView = (ListView)findViewById(R.id.list_offline);
        listOffline = new ArrayList<>();
        File dirFiles = MainOffline.this.getFilesDir();
        for (String strFile : dirFiles.list())
        {
            // strFile is the file name
            Log.d("myLog","test trong main offline:" + strFile);
            listOffline.add(strFile);
            //String s = new SoftFile(Main.this,strFile).GetContentFile();
            //Log.d("myLog",strFile + ":\n" + s);
        }
        for(String s:listOffline){
            Log.d("myLog","test:" + s);
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOffline);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new listClick());

        registerForContextMenu(listView);

    }

    class listClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainOffline.this, TestWebview.class);
            intent.putExtra("read_offline",listOffline.get(position));
            startActivity(intent);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.offline, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final String filename = listOffline.get(info.position);
        Log.d("myLog","đã chọn context offline");
        switch (item.getItemId()){
            case R.id.xoa_offline:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                //final View addView = getLayoutInflater().inflate(R.layout.add, null);
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int
                                    whichButton) {
                                String dir = getFilesDir().getAbsolutePath();
                                File f0 = new File(dir, filename);
                                boolean d0 = f0.delete();
                                if(d0 == true){
                                    Toast.makeText(MainOffline.this, "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
                                }
                                for(int i = 0; i < listOffline.size(); i ++){
                                    if(listOffline.get(i) == filename){
                                        listOffline.remove(i);
                                    }
                                }
                                adapter.notifyDataSetChanged();


                            }
                        }
                );
                dialog.setNegativeButton("Cancel", null);
                dialog.show();
                dialog.setTitle("Xóa bài viết?");

        }
        return true;
    }
}
