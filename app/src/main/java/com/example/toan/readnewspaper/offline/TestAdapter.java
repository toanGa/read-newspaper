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
import android.widget.ListView;
import android.widget.Toast;

import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.database.NewspaperHelper;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;
import com.example.toan.readnewspaper.webview.TestWebview;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by toan on 17/12/2016.
 */

public class TestAdapter extends Activity {
    ListView list;
    String[] title;
    ArrayList<ItemOnDB> itemOnDBs;
    ListOfflineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);


        itemOnDBs = new ArrayList<>();
        list=(ListView)findViewById(R.id.list);

        NewspaperHelper helper = new NewspaperHelper(this);

        itemOnDBs = helper.GetListItemOnDB();
        int length = 0;
        try{
            length = itemOnDBs.size();
        }catch (Exception e){
            Toast.makeText(this, "Chưa có file được lưu", Toast.LENGTH_SHORT).show();
        }

        title = new String[length];

        for(int i = 0; i < length; i ++){
            title[i] = itemOnDBs.get(i).title;
            Log.d("mylog",i + "," + title[i]);
            //arrayListTitle.add(itemOnDBs.get(i).title);
        }
        adapter = new ListOfflineAdapter(TestAdapter.this,title,itemOnDBs);


        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TestAdapter.this, "You Clicked at " +title[+position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TestAdapter.this, TestWebview.class);
                intent.putExtra("read_offline",itemOnDBs.get(position).htmlFileName);
                startActivity(intent);

            }
        });
        registerForContextMenu(list);

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
        final String filename = itemOnDBs.get(info.position).htmlFileName;
        final String imageFileName = itemOnDBs.get(info.position).imageFileName;

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
                                File f1 = new File(dir,imageFileName);
                                boolean d0 = f0.delete();
                                if(d0 == true){
                                    Toast.makeText(TestAdapter.this, "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
                                }
                                if(f1.delete()){
                                    Toast.makeText(TestAdapter.this, "Đã xóa anh bài viết", Toast.LENGTH_SHORT).show();
                                }

                                NewspaperHelper helper = new NewspaperHelper(TestAdapter.this);
                                title = new String[itemOnDBs.size() - 1];
                                int length = itemOnDBs.size();

                                for(int i = 0; i < length; i ++){
                                    //j ++;
                                    if(itemOnDBs.get(i).htmlFileName == filename){
                                        itemOnDBs.remove(i);
                                        helper.DeleteSavedByHtmlFileName(filename);
                                        File file = new File(TestAdapter.this.getFilesDir() + "/" + filename);
                                        if(file.delete()){
                                            Log.d("mylog","da xoa noi dung file");
                                        }
                                        file = new File(TestAdapter.this.getFilesDir() + "/" + filename + ".jpg");
                                        if(file.delete()){
                                            Log.d("mylog","da xoa anh cua file");
                                        }
                                        i = length;
                                    }
                                }
                                for(String s : title){
                                    Log.d("myLog","title sau khi da xoa:" + s);
                                }
                                /////////////////////
                                Log.d("mylog","chieu dai string trươc khi xóa:" + title.length);
//                                int size = itemOnDBs.size();
//                                String newtitle[] = null;
//                                newtitle = new String[size];
//
//                                for(int i = 0; i < size; i ++){
//                                    newtitle[i] = itemOnDBs.get(i).title;
//                                    Log.d("mylog",i + "," + newtitle[i]);
//                                    //arrayListTitle.add(itemOnDBs.get(i).title);
//                                }
//                                adapter = new ListOfflineAdapter(TestAdapter.this,newtitle,itemOnDBs);
//                                adapter.notifyDataSetChanged();
                                ////////////////////////
                                int size = itemOnDBs.size();
                                title = null;
                                title = new String[size];
                                //title = ArrayUtils.removeElement(array, element)
                                Log.d("mylog","chieu dai string sau khi xóa:" + title.length);
                                for(int i = 0; i < size; i ++){
                                    title[i] = itemOnDBs.get(i).title;
                                    Log.d("mylog",i + "," + title[i]);
                                    //arrayListTitle.add(itemOnDBs.get(i).title);
                                }
                                //adapter = new ListOfflineAdapter(TestAdapter.this,title,itemOnDBs);
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
