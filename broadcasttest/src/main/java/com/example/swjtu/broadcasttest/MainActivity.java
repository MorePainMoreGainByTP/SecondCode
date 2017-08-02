package com.example.swjtu.broadcasttest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String PROVIDER_URI = "content://com.example.swjtu.secondcodeprovider/";

    private String newId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "uncaughtException: ",e );
            }
        });
    }

    public void OnAllBook(View v) {
        //Toast.makeText(this, "OnAllBook", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(PROVIDER_URI + "book2");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.i(TAG, "author: " + author + ",name:" + name + ",price:" + price);
            }
            cursor.close();
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnBookItem(View v) {
        Uri uri = Uri.parse(PROVIDER_URI + "book2/"+newId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.i(TAG, "author: " + author + ",name:" + name + ",price:" + price);
            }
            cursor.close();
        }else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnAddBook(View v){
        Uri uri = Uri.parse(PROVIDER_URI + "book2");
        ContentValues values = new ContentValues();
        values.put("author","tangpeng");
        values.put("name","myC++");
        values.put("price",0.01);
        Uri newUri = getContentResolver().insert(uri,values);
        if(newUri != null)
        {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            newId = newUri.getPathSegments().get(1);
        }else{
            Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
        }
    }
    public void OnAllCategory() {

    }

    public void OnCategoryItem() {

    }
}
