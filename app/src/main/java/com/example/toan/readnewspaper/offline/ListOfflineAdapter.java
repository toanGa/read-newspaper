package com.example.toan.readnewspaper.offline;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toan.readnewspaper.R;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;

import java.util.ArrayList;

/**
 * Created by toan on 17/12/2016.
 */

public class ListOfflineAdapter extends ArrayAdapter<String> {
    private Activity context;
    String[] title;
    ArrayList<ItemOnDB> itemOnDBs;
    //ArrayList<String> test;
    //ArrayList<String> arrayList = null;
    public ListOfflineAdapter(Activity context, String[] title,ArrayList<ItemOnDB> itemOnDBs){
        super(context, R.layout.list_single, title);
        Log.d("mylog","khoi tao list adapter");
        this.itemOnDBs = new ArrayList<>();

        this.context = context;
        this.title = title;
        this.itemOnDBs = itemOnDBs;

        Log.d("mylog","khoi tao xong list adapter");

        //test = new ArrayList<>();


    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("mylog","start at get view");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

       ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(title[position]);
        try{
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), context.getFileStreamPath(itemOnDBs.get(position).imageFileName).getAbsolutePath());
            imageView.setImageDrawable(bitmapDrawable);
        }catch (Exception e){
            Log.d("myLog",e.toString());
        }

        Log.d("mylog","getview xong");
        return rowView;
        //return null;
    }
}
