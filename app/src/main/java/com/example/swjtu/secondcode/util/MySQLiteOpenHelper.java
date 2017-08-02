package com.example.swjtu.secondcode.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tangpeng on 2017/2/18.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteOpenHelper";

    private static final String CREATE_TABLE_BOOK = "create table book2(id integer primary key autoincrement,name varchar(20),author varchar(20),price double)";


    private static final String CREATE_TABLE_USERS = "create table users(id integer primary key autoincrement,user_name varchar(20),password varchar(20))";
    private static final String CREATE_TABLE_RIGHTS = "create table rights(id integer primary key autoincrement,right_NO integer,right_name varchar(20),right_module varchar(20),win_name varchar(255))";
    private static final String CREATE_TABLE_USER_RIGHTS = "create table user_rights(id integer primary key autoincrement," +
            "user_ID integer references users(id) on update cascade on delete cascade,right_ID integer references rights(id) on update cascade on delete cascade)";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        //第一次使用数据库，创建所需的表
//        //创建用户表
        db.execSQL(CREATE_TABLE_BOOK);
//        //创建权限表
//        db.execSQL(CREATE_TABLE_RIGHTS);
//        //创建用户权限分配表
//        db.execSQL(CREATE_TABLE_USER_RIGHTS);
//        Log.i(TAG, "MySQLiteOpenHelper: 第一次使用数据库创建表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
