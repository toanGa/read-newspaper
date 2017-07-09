package com.example.toan.readnewspaper.dantri;

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
import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.empty.CustomList;
/**
 * Created by toan on 10/12/2016.
 */

public class DantriList extends Activity{
    ListView list;//list view hiển thị giao diện các chuyên mục
    /**
     *  Đọc các chuyên mục trang chủ,thể thao,bóng đá anh,...
     *  từ trang dân trí
     */
    String[] web = {
            "trang chu",
            "the thao",
            "bong da anh",
            "bong da tay ban nha",
            "bong da chau au",
            "kinh doanh",
            "doanh nghiep",///
            "thi truong",
            "quoc te",
            "tai chinh dau tu",
            "khoi nghiep",
            "phan mem bao mat"
    } ;

    /**
     *  id của ảnh tương ứng với các chuyên mục
     *
     */
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
            R.drawable.image7

    };

    /**
     *  Link của các chuyên mục
     *
     */
    String[] linkRead = {
        "http://dantri.com.vn/trangchu.rss",
        "http://dantri.com.vn/the-thao.rss",
        "http://dantri.com.vn/the-thao/bong-da-anh.rss",
        "http://dantri.com.vn/the-thao/bong-da-tbn.rss",
        "http://dantri.com.vn/the-thao/bong-da-chau-au.rss",
        "http://dantri.com.vn/kinh-doanh.rss",
        "http://dantri.com.vn/kinh-doanh/doanh-nghiep.rss",
        "http://dantri.com.vn/kinh-doanh/thi-truong.rss",
        "http://dantri.com.vn/kinh-doanh/quoc-te.rss",
        "http://dantri.com.vn/kinh-doanh/tai-chinh-dau-tu.rss",
        "http://dantri.com.vn/kinh-doanh/khoi-nghiep.rss",
        "http://dantri.com.vn/suc-manh-so/phan-mem-bao-mat.rss"
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_single);

        CustomList adapter = new
                CustomList(DantriList.this, web, imageId,linkRead);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DantriList.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                // chuyển đến intent để parse rss thành các bài viết cùng chuyên mục
                Intent intent = new Intent(DantriList.this, DantriExecute.class);

                Bundle myBundle = new Bundle();
                myBundle.putString("read_link",linkRead[position]);//truyền link đến để activiti mới pasre thành các bài viết
                myBundle.putString("dantri_type",web[position]);
                Log.d("myLog",linkRead[position]);
                intent.putExtras(myBundle);

                startActivity(intent);

            }
        });

        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);// creat context menu để lưu bài viết
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

}
