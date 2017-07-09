package com.example.toan.readnewspaper.test;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.toan.readnewspaper.R;

import java.io.File;

/**
 * Created by toan on 05/12/2016.
 */

public class TestSave extends Activity {
    ImageView imageView;
    String url = "https://www.morroccomethod.com/components/com_virtuemart/shop_image/category/resized/Trial_Sizes_4e4ac3b0d3491_175x175.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        imageView = (ImageView)findViewById(R.id.imgPicker);
        File file = getBaseContext().getFileStreamPath("photo.jpg");

        if (file.exists()){
            Log.d("myLog","can be luu");
            if(file.delete())
                Log.d("myLog","da xoa file");
        }else{
            Log.d("myLog","chua co file");
            new SaveImageToInternal(url,TestSave.this,"photo.jpg");
            Log.d("myLog","thuc thi xong");
        }

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getFileStreamPath("photo.jpg").getAbsolutePath());
        imageView.setImageDrawable(bitmapDrawable);

    }
}
