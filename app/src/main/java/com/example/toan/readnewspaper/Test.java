package com.example.toan.readnewspaper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import com.example.toan.readnewspaper.database.NewspaperHelper;

/**
 * Created by toan on 26/11/2016.
 */

public class Test extends Activity {
    NewspaperHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        helper = new NewspaperHelper(this);
        //helper.SaveNewspaper("bong da","hom nay la mot ngay vo cung tuyet voi","c/d/f","20-11");
        //helper.AutoSaveLastNewspaper("dan tri","doi thuong" ,"ga troi","tinh thu ga troi","///","10-3");
        //helper.DeleteNewsPaper(1);
        //helper.DeleteSavedByTitle("bong da");
        //helper.DeleteLastSavedById(4);

    }

    public void clickMe(View view){
        String s = helper.showAllData();
        Log.d("myLog","log is" + s);
        Log.d("myLog","second :" + helper.showDataSavedLast());
    }
}
