package com.example.toan.readnewspaper.vnexpress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.empty.CustomList;

/**
 * Created by toan on 12/12/2016.
 */

public class VnExpressList extends AppCompatActivity {
    ListView list;

    String[] web = {
            "trang chu",
            "thoi su",
            "the gioi",
            "kinh doanh",
            "giai tri",
            "the thao",
            "phap luat",///
            "giao duc",
            "suc khoe",
            "gia dinh",
            "du lich",
            "khoa hoc",
            "so hoa",
            "xe",
            "cong dong",
            "tam su",
            "cuoi"
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
            R.drawable.image4

    };
    String[] linkRead = {
            "http://vnexpress.net/rss/tin-moi-nhat.rss",
            "http://vnexpress.net/rss/thoi-su.rss",
            "http://vnexpress.net/rss/the-gioi.rss",
            "http://vnexpress.net/rss/kinh-doanh.rss",
            "http://vnexpress.net/rss/giai-tri.rss",
            "http://vnexpress.net/rss/the-thao.rss",
            "http://vnexpress.net/rss/phap-luat.rss",
            "http://vnexpress.net/rss/giao-duc.rss",
            "http://vnexpress.net/rss/suc-khoe.rss",
            "http://vnexpress.net/rss/gia-dinh.rss",
            "http://vnexpress.net/rss/du-lich.rss",
            "http://vnexpress.net/rss/khoa-hoc.rss",
            "http://vnexpress.net/rss/so-hoa.rss",
            "http://vnexpress.net/rss/oto-xe-may.rss",
            "http://vnexpress.net/rss/cong-dong.rss",
            "http://vnexpress.net/rss/tam-su.rss",
            "http://vnexpress.net/rss/cuoi.rss"
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);

        CustomList adapter = new
                CustomList(VnExpressList.this, web, imageId,linkRead);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(VnExpressList.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                //DanTriChuyenMuc ddddd = new DanTriChuyenMuc(linkRead[position]);
                Intent intent = new Intent(VnExpressList.this, VnExpressExecute.class);

                Bundle myBundle = new Bundle();
                myBundle.putString("read_link",linkRead[position]);
                myBundle.putString("vnexpress_type",web[position]);
                Log.d("myLog",linkRead[position]);
                intent.putExtras(myBundle);

                startActivity(intent);

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
