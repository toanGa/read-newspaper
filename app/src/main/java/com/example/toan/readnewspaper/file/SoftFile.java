package com.example.toan.readnewspaper.file;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by toan on 09/12/2016.
 */

public class SoftFile  {
    Context context;
    private static String filename ;

    public SoftFile(Context context,String filename){
        this.filename = filename;
        this.context = context;
    }

    /**
     *
     *
     * @param
     * @return
     */
    public String GetContentFile(){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String str;

                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str + "\n");
                }
                inputStream.close();

                Log.d("myLog", " file ton tai");
            }
        } catch (Exception ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("myLog", "error to read file");
        }
        return stringBuffer.toString();

    }

    /**
     *
     *
     */
    public void SetContentFile(String content){
        try {
            OutputStreamWriter out = new OutputStreamWriter(
                    context.openFileOutput(filename, 0));
            out.write(content);
            out.close();
            Log.d("myLog","da ghi vao file");
        } catch (Throwable t) {
            Log.d("myLog","khong the ghi vao file");
        }
    }


}
