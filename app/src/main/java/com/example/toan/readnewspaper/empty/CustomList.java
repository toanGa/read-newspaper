package com.example.toan.readnewspaper.empty;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toan.readnewspaper.R;

/**
 * Created by toan on 04/12/2016.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final String[] linkRead;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId,String[] linkRead) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.linkRead = linkRead;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        Resources resources = context.getResources();
        //imageView.setImageResource(imageId[position]);
        imageView.setImageDrawable(resources.getDrawable(imageId[position]));
        return rowView;
    }
}