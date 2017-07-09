package com.example.toan.readnewspaper.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by toan on 05/12/2016.
 */

public class SaveImageToInternal {
    String url;
    Context context = null;
    String nameFileOutput;
    public SaveImageToInternal(String url,Context context,String nameFileOutput){
        this.url = url;
        this.context = context;
        this.nameFileOutput = nameFileOutput;
        new DownloadImageTask().execute(url);
    }

    public boolean saveImageToInternalStorage(Bitmap image, Context context)
    {

        try {
            FileOutputStream fos = context.openFileOutput(nameFileOutput, Context.MODE_WORLD_READABLE);
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


        public DownloadImageTask() {

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

            //bmImage.setImageBitmap(result);
            try{
                saveImageToInternalStorage(mIcon11,context);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("myLog","thats error");
            }


        }}
}
