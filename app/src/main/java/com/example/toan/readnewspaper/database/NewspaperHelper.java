package com.example.toan.readnewspaper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.toan.readnewspaper.Newspaper;
import com.example.toan.readnewspaper.db_save_item.ItemOnDB;

import java.util.ArrayList;

/**
 * Created by toan on 26/11/2016.
 */

public class NewspaperHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="newspaper.db";
    private static final int SCHEMA_VERSION=1;

    public NewspaperHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists saved_last " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, spec TEXT, title TEXT, content TEXT, image_link TEXT, public_date TEXT);"
        );

        db.execSQL("CREATE TABLE if not exists saved " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, image_link TEXT, public_date TEXT);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SaveNewspaper(Newspaper newspaper){
        //String title,String content,String imageLink,String publicDate
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title", newspaper.title);
        cv.put("content", newspaper.content);
        cv.put("image_link", newspaper.imageLink);
        cv.put("public_date", newspaper.publicDate);

        db.insert("saved", "title", cv);
    }

    public void SaveItemOnDB(ItemOnDB itemOnDB){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", itemOnDB.title);
        cv.put("content", itemOnDB.htmlFileName);
        cv.put("image_link", itemOnDB.imageFileName);

        db.insert("saved", "title", cv);

        Log.d("myLog", itemOnDB.title + "\n" + itemOnDB.htmlFileName + "\n" + itemOnDB.imageFileName);
    }
    public void DeleteSavedById(long rowId){
        SQLiteDatabase db = getWritableDatabase();
        String[] args={String.valueOf(rowId)};
        db.delete("saved", "_ID=?", args);
    }

    public void DeleteSavedByTitle(String titile){
        SQLiteDatabase db = getWritableDatabase();
        String[] args={String.valueOf(titile)};
        db.delete("saved", "title=?", args);
    }
    public void DeleteSavedByHtmlFileName(String htmlFileName){
        SQLiteDatabase db = getWritableDatabase();
        String[] args={String.valueOf(htmlFileName)};
        db.delete("saved", "content=?",args);

    }
    public Cursor getSavedCursor(){
        String str = "SELECT * FROM saved ;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(str,null);

        return cursor;
    }

    public ArrayList<ItemOnDB> GetListItemOnDB(){
        ItemOnDB itemOnDB = new ItemOnDB(null,null,null);
        ArrayList<ItemOnDB> listItems = new ArrayList<>();
        String str = "SELECT * FROM saved ORDER BY title;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(str,null);
        if(cursor.getCount() == 0){
            return  null;
        }
        cursor.moveToNext();
        while (!cursor.isAfterLast()){

            Log.d("myLog","data is reading\n");

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String imageLink = cursor.getString(3);

            listItems.add(new ItemOnDB(title,content,imageLink));
            String data = id + " " + title + " " + content + ", " + imageLink + "\n";
            Log.d("myLog","item on database" + data);
            cursor.moveToNext();

        }
        return listItems;
    }
    public String showAllData(){
        String data = "";
        String str = "SELECT * FROM saved ORDER BY title;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(str,null);
        if(cursor.getCount() == 0){
            return  data;
        }
        cursor.moveToNext();
        while (!cursor.isAfterLast()){

            Log.d("myLog","data is reading\n");

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);

            data += id + " " + title + " " + content + "\n";
            cursor.moveToNext();

        }
        return  data;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    public void AutoSaveLastNewspaper(String type,String spec,String title,String content,String imageLink,String publicDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("type",type);
        cv.put("spec",spec);
        cv.put("title", title);
        cv.put("content", content);
        cv.put("image_link", imageLink);
        cv.put("public_date", publicDate);

        db.insert("saved_last", "type", cv);
    }

    public String showDataSavedLast(){
        String data = "";
        String str = "SELECT * FROM saved_last;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(str,null);
        if(cursor.getCount() == 0){
            return  data;
        }
        cursor.moveToNext();
        while (!cursor.isAfterLast()){

            Log.d("myLog","data is reading\n");

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            data += id + " " + title + " " + content + "\n";
            cursor.moveToNext();

        }
        return  data;
    }
    public void DeleteLastSavedById(long rowId){
        SQLiteDatabase db = getWritableDatabase();
        String[] args={String.valueOf(rowId)};
        db.delete("saved_last", "_ID=?", args);
    }

    public void DeleteLastSavedByTitle(String titile){
        SQLiteDatabase db = getWritableDatabase();
        String[] args={String.valueOf(titile)};
        db.delete("saved_last", "title=?", args);
    }

    Newspaper getNewspaper(String type,String spec){
        Newspaper newspaper = null;
        String str = "SELECT * FROM saved_last WHERE type="+ type + ", spec=" + spec +";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(str,null);
        if(cursor.getCount() == 0){
            return  newspaper;
        }
        cursor.moveToNext();
        while (!cursor.isAfterLast()){

            Log.d("myLog","data is reading\n");

            //int id = cursor.getInt(0);
            newspaper.type = cursor.getString(1);
            newspaper.spec  = cursor.getString(2);
            newspaper.title = cursor.getString(3);
            newspaper.content = cursor.getString(4);
            newspaper.imageLink = cursor.getString(5);
            newspaper.publicDate = cursor.getString(6);
            cursor.moveToNext();

        }

        return newspaper;
    }
}
