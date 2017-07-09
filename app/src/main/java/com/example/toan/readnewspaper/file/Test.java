package com.example.toan.readnewspaper.file;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.toan.readnewspaper.R;


/**
 * Created by toan on 09/12/2016.
 */

public class Test extends Activity {
    String filename = "test.txt";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        SoftFile softFile = new SoftFile(this,filename);
        softFile.SetContentFile("tinh hinh la the nay\n anh em a");
        Log.d("myLog",softFile.GetContentFile());
    }
}
