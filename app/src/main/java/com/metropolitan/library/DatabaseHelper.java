package com.metropolitan.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "projektni.db";
    public static final String TABLE_NAME = "books";

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String AUTHOR = "AUTHOR";
    public static final String PAGES = "PAGES";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String SHORT_DESC = "SHORT_DESC";
    public static final String LONG_DESC = "LONG_DESC";
    public static final String LINK = "LINK";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AUTHOR TEXT, PAGES INTEGER, IMAGE_URL TEXT, SHORT_DESC TEXT, LONG_DESC TEXT, LINK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertBook(String name,String author, int pages, String imageUrl, String shortDesc, String longDesc, String link){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AUTHOR, author);
        contentValues.put(PAGES, pages);
        contentValues.put(IMAGE_URL, imageUrl);
        contentValues.put(SHORT_DESC, shortDesc);
        contentValues.put(LONG_DESC, longDesc);
        contentValues.put(LINK, link);
        long jeLi = db.insert(TABLE_NAME, null, contentValues);
        if(jeLi == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllBooks(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String QUERY = " SELECT ID, NAME, AUTHOR, PAGES, IMAGE_URL, SHORT_DESC, LONG_DESC, LINK FROM " + TABLE_NAME;

        Cursor res = sqLiteDatabase.rawQuery(QUERY, null);
        return res;
    }

    public Cursor getBookById(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String QUERY = " SELECT ID, NAME, AUTHOR, PAGES, IMAGE_URL, SHORT_DESC, LONG_DESC, LINK FROM " + TABLE_NAME + " WHERE ID = " + id;
        Cursor res = sqLiteDatabase.rawQuery(QUERY, null);
        return res;
    }

    public boolean deleteBook(String id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int isDeleted = sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] { id });
        if(isDeleted == 0 ){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateBook(String id, String name, String author, String pages, String imageUrl, String shortDesc, String longDesc, String link) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AUTHOR, author);
        contentValues.put(PAGES, pages);
        contentValues.put(IMAGE_URL, imageUrl);
        contentValues.put(SHORT_DESC, shortDesc);
        contentValues.put(LONG_DESC, longDesc);
        contentValues.put(LINK, link);
        long jeLi = sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        if(jeLi == -1){
            return false;
        }else{
            return true;
        }
    }
}
