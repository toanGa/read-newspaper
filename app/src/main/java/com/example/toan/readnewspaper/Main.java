package com.example.toan.readnewspaper;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.toan.readnewspaper.cnn.CnnList;
import com.example.toan.readnewspaper.constant.Constant;
import com.example.toan.readnewspaper.dantri.DantriExecute;
import com.example.toan.readnewspaper.dantri.DantriList;
import com.example.toan.readnewspaper.file.SoftFile;
import com.example.toan.readnewspaper.offline.MainOffline;
import com.example.toan.readnewspaper.offline.TestAdapter;
import com.example.toan.readnewspaper.vnexpress.VnExpressList;
import com.example.toan.readnewspaper.webview.TestWebview;

import java.io.File;

/**
 * Created by toan on 23/11/2016.
 */

public class Main extends Activity {
    ImageView dantri;
    ImageView saf;
    ImageView i24h;
    ImageView baomoi;
    ImageView kenh14;
    ImageView vnexpress;
    ImageView bbc;
    ImageView cnn;
    Constant constant;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Log.d("myLog","lay out done");
        constant = new Constant();

        dantri = (ImageView)findViewById(R.id.imv_dantri);
        saf = (ImageView)findViewById(R.id.imv_saf);
        i24h = (ImageView)findViewById(R.id.imv_24h);
        baomoi = (ImageView)findViewById(R.id.imv_baomoi);
        kenh14 = (ImageView)findViewById(R.id.imv_kenh14);
        vnexpress = (ImageView)findViewById(R.id.imv_vnexpress);
        bbc = (ImageView)findViewById(R.id.imv_bbc);
        cnn = (ImageView)findViewById(R.id.imv_cnn);

        dantri.setOnClickListener(new dantriClick());
        vnexpress.setOnClickListener(new VnExpressClick());
        cnn.setOnClickListener(new CnnClick());

        File dirFiles = Main.this.getFilesDir();
        for (String strFile : dirFiles.list())
        {
            // strFile is the file name
            Log.d("myLog","cac file da luu:" + strFile);
            //String s = new SoftFile(Main.this,strFile).GetContentFile();
            //Log.d("myLog",strFile + ":\n" + s);
        }
    }

    class dantriClick implements View.OnClickListener{

        //TestWebview webview;
        @Override
        public void onClick(View v) {
            //webview = new TestWebview();
            if(isNetworkAvailable())//nếu bật mạng thì show chế độ online
            try{//đọc trang dân trí
                Intent intent = new Intent(Main.this, DantriList.class);
                intent.putExtra("type",constant.DANTRI);
                startActivity(intent);
            }catch (Exception e){
                Log.d("myLog",e.toString());
            }
            else{
                File dirFiles = Main.this.getFilesDir();// kiểm tra file đang có trong project
                for (String strFile : dirFiles.list())
                {
                    // strFile is the file name
                    Log.d("myLog","cac file da luu:" + strFile);
                    //String s = new SoftFile(Main.this,strFile).GetContentFile();
                    //Log.d("myLog",strFile + ":\n" + s);
                }
                Intent intent = new Intent(Main.this, TestAdapter.class);//Khởi tạo chế độ đọc offline
                //intent.putExtra("type_webview","dan_tri");
                startActivity(intent);
            }
        }
    }
    class VnExpressClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(isNetworkAvailable())
                try{
                    Intent intent = new Intent(Main.this, VnExpressList.class);
                    intent.putExtra("type",constant.VNEXPRESS);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("myLog",e.toString());
                }
            else{
                File dirFiles = Main.this.getFilesDir();
                for (String strFile : dirFiles.list())
                {
                    // strFile is the file name
                    Log.d("myLog","cac file da luu:" + strFile);
                    //String s = new SoftFile(Main.this,strFile).GetContentFile();
                    //Log.d("myLog",strFile + ":\n" + s);
                }
                Intent intent = new Intent(Main.this, TestAdapter.class);
                startActivity(intent);
            }
        }
    }

    class CnnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(isNetworkAvailable())
                try{
                    Intent intent = new Intent(Main.this, CnnList.class);
                    intent.putExtra("type",constant.CNN);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("myLog",e.toString());
                }
            else{
                File dirFiles = Main.this.getFilesDir();
                for (String strFile : dirFiles.list())
                {
                    // strFile is the file name
                    Log.d("myLog","cac file da luu:" + strFile);
                    //String s = new SoftFile(Main.this,strFile).GetContentFile();
                    //Log.d("myLog",strFile + ":\n" + s);
                }
                Intent intent = new Intent(Main.this, TestAdapter.class);
                startActivity(intent);
            }
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
