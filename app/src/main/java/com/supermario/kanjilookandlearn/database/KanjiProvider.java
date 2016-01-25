package com.supermario.kanjilookandlearn.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.supermario.kanjilookandlearn.common.Utils;
import com.supermario.kanjilookandlearn.data.Kanji;

import java.util.ArrayList;



public class KanjiProvider {
    public final static String TABLE_NAME = "Kanji";
    public final static String DEFAULT_SORT_ORDER = Columns.KANJI;

    public final class Columns implements BaseColumns {
        public final static String ID = "id";
        public final static String KANJI = "kanji";
        public final static String NAME = "name";
        public final static String ONYOMI = "onyomi";
        public final static String KUNYOMI = "kunyomi";
        public final static String MEAN_VIETNAMESE = "mean_vietnamese";
        public final static String MEAN_ENGLISH = "mean_english";
        public final static String EXAMPLE = "example";
        public final static String IMAGE = "image";
        public final static String IMAGE_WRITE = "image_write";
    }


    public static ArrayList<Kanji> getAll(Context context) {
        DatabaseHandler db = DatabaseHandler.getDatabase(context);

        Cursor c = db.query(TABLE_NAME, null, null, null, DEFAULT_SORT_ORDER);
        if (c == null) {
            return null;
        }
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();

        ArrayList<Kanji> areaArray = new ArrayList<Kanji>();
        Kanji b = null;
        while (!c.isAfterLast()) {
            b = new Kanji();
            b.id = Utils.getCursorInt(c, Columns.ID);
            b.name = Utils.getCursorString(c, Columns.NAME);
            b.kanji = Utils.getCursorString(c, Columns.KANJI);
            b.onyomi = Utils.getCursorString(c, Columns.ONYOMI);
            b.kunyomi = Utils.getCursorString(c, Columns.KUNYOMI);
            b.meanVietnamese = Utils.getCursorString(c, Columns.MEAN_VIETNAMESE);
            b.meanEnglish = Utils.getCursorString(c, Columns.MEAN_ENGLISH);
            b.example = Utils.getCursorString(c, Columns.EXAMPLE);
            b.image = Utils.getCursorString(c, Columns.IMAGE);
            b.imageWrite = Utils.getCursorString(c, Columns.IMAGE_WRITE);

            areaArray.add(b);
            c.moveToNext();
        }
        c.close();

        return areaArray;
    }

    public static ArrayList<Kanji> searchArea(Context context, String searchKey) {
        DatabaseHandler db = DatabaseHandler.getDatabase(context);
        char[] chars= searchKey.toCharArray();
        String selection="";
        for(int i = 0; i <  chars.length; i++){
            if(i == chars.length - 1){
                selection += Columns.NAME + " LIKE '%" + chars[i]  + "%'   OR " + Columns.KANJI + " LIKE '%" + chars[i] + "%' ";
            }else{
                selection += Columns.NAME + " LIKE '%" + chars[i]  + "%'   OR " + Columns.KANJI + " LIKE '%" + chars[i] + "%' OR ";
            }

        }
        Cursor c = db.query(TABLE_NAME, null, selection, null, DEFAULT_SORT_ORDER, "50");
        if (c == null) {
            return null;
        }
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();

        ArrayList<Kanji> areaArray = new ArrayList<Kanji>();
        Kanji b = null;
        while (!c.isAfterLast()) {
            b = new Kanji();
            b.id = Utils.getCursorInt(c, Columns.ID);
            b.name = Utils.getCursorString(c, Columns.NAME);
            b.kanji = Utils.getCursorString(c, Columns.KANJI);
            b.onyomi = Utils.getCursorString(c, Columns.ONYOMI);
            b.kunyomi = Utils.getCursorString(c, Columns.KUNYOMI);
            b.meanVietnamese = Utils.getCursorString(c, Columns.MEAN_VIETNAMESE);
            b.meanEnglish = Utils.getCursorString(c, Columns.MEAN_ENGLISH);
            b.example = Utils.getCursorString(c, Columns.EXAMPLE);
            b.image = Utils.getCursorString(c, Columns.IMAGE);
            b.imageWrite = Utils.getCursorString(c, Columns.IMAGE_WRITE);

            areaArray.add(b);
            c.moveToNext();
        }
        c.close();

        return areaArray;
    }



    private static boolean isExist(Context context, Kanji diary) {
        // TODO Auto-generated method stub
        return false;
    }
}
