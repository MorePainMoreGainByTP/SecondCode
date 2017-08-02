package com.example.swjtu.secondcode.contentResolver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.swjtu.secondcode.util.MySQLiteOpenHelper;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.swjtu.secondcodeprovider";

    public static final int BOOK_DIR = 0;   //表中的所有数据
    public static final int BOOK_ITEM = 1;  //表中的一行数据
    public static final int CATEGORY_DIR = 2;  //表中的所有数据
    public static final int CATEGORY_ITEM = 3;  //表中的一行数据

    private static UriMatcher uriMatcher;

    private MySQLiteOpenHelper mySQLiteOpenHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book2", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book2/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("book2", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("book2", "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows = db.delete("category", "id = ?", new String[]{categoryId});
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.swjtu.secondcodeprovider.book2";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.swjtu.secondcodeprovider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.swjtu.secondcodeprovider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.swjtu.secondcodeprovider.category";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = sqLiteDatabase.insert("Book2", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book2/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = sqLiteDatabase.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/Category/" + newCategoryId);
                break;
        }

        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext(), "BookStore2.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = sqLiteDatabase.query("Book2", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("Book2", projection, "id = ?", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = sqLiteDatabase.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String category = uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("Category", projection, "id = ?", new String[]{category}, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("book2", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updateRows = db.update("book2", values, "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update("category", values, "id = ?", new String[]{categoryId});
                break;
        }
        return updateRows;
    }
}
