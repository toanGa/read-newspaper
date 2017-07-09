package com.example.toan.readnewspaper.test;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import com.example.toan.readnewspaper.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.spec.ECField;

/**
 * Created by toan on 27/11/2016.
 */

public class SaveImage extends Activity {
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
        }else{
            new DownloadImageTask(imageView).execute(url);
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getFileStreamPath("photo.jpg").getAbsolutePath());
        imageView.setImageDrawable(bitmapDrawable);

    }

    public boolean saveImageToInternalStorage(Bitmap image,Context context)
    {

        try {
            FileOutputStream fos = context.openFileOutput("photo.jpg", Context.MODE_WORLD_READABLE);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // 100 means no compression, the lower you go, the stronger the compression
            fos.close();
            return true;
        }
        catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
        }
        return false;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        Bitmap mIcon11 = null;
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }


/*    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        showProgressDialog();
    }*/

        protected void onPostExecute(Bitmap result) {
            //pDlg.dismiss();
            bmImage.setImageBitmap(result);
            try{
                saveImageToInternalStorage(mIcon11,SaveImage.this);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("myLog","thats error");
            }

            //return  mIcon11;
        }}
}
