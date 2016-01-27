package com.supermario.kanjilookandlearn.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHandler {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Kanji";
    private static String DATABASE_PATH = "data/data/com.supermario.kanjilookandlearn/databases/";

    private static DatabaseHandler mInstance = new DatabaseHandler();

    private static SQLiteDatabase mWritable;
    private static SQLiteDatabase mReadable;

    public synchronized static DatabaseHandler getDatabase(Context context) {
        if ((mWritable == null) || (mReadable == null)) {
            String path = DATABASE_NAME;
            DatabaseHelper helper = new DatabaseHelper(context, path);
            if (mWritable == null) {
                mWritable = helper.getWritableDatabase();
            }
            if (mReadable == null) {
                mReadable = helper.getReadableDatabase();
            }
        }
        return mInstance;
    }

    @Override
    protected void finalize() {
        if ((mWritable != null) && mWritable.isOpen()) {
            mWritable.close();
        }
        if ((mReadable != null) && mReadable.isOpen()) {
            mReadable.close();
        }
    }

    private static final class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            updateDatabase(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        private void updateDatabase(SQLiteDatabase db) {

        }

    }

    public SQLiteDatabase getReadableDatabase() {
        return mReadable;
    }

    public SQLiteDatabase getWritableDatabase() {
        return mWritable;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long rowId = mWritable.insert(table, nullColumnHack, values);
        return rowId;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int count = mWritable.delete(table, whereClause, whereArgs);
        return count;
    }

    public int update(String table, ContentValues values, String whereClause,
                      String[] whereArgs) {
        int count = mWritable.update(table, values, whereClause, whereArgs);
        return count;
    }

    public Cursor query(String table, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c = mReadable.query(table, projection, selection, selectionArgs,
                null, null, sortOrder);
        return c;
    }

    public Cursor query(String table, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder, String limit) {
        Cursor c = mReadable.query(table, projection, selection, selectionArgs,
                null, null, sortOrder, limit);
        return c;
    }

    public static void createDB(Context context) throws IOException {
        try {

            InputStream ip = context.getAssets().open(DATABASE_NAME + ".db");
            Log.i("Input Stream....", ip + "");
            File dir = new File(DATABASE_PATH);
            if(!dir.exists()){
                dir.mkdirs(); //added
            }

            String op = DATABASE_PATH + DATABASE_NAME;
            OutputStream output = new FileOutputStream(op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            ip.close();
        } catch (IOException e) {
            Log.v("error", e.toString());
        }


    }

    public static boolean isHavingDatabase(){
        String op = DATABASE_PATH + DATABASE_NAME;
        File file = new File(op);
        if(file.exists()) {
            return true;
        }
        return false;
    }

}