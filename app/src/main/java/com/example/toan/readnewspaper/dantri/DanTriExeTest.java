package com.example.toan.readnewspaper.dantri;

import android.os.Bundle;

import com.example.toan.readnewspaper.webview.ContentExecute;

/**
 * Created by toan on 15/12/2016.
 */

public class DanTriExeTest extends ContentExecute {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exe_type = "dan_tri";
    }
}
