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
    public final static String DEFAULT_SORT_ORDER = Columns.ID;

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
        public final static String REMEMBER = "remember";
        public final static String FAVORITE = "favorite";
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

        ArrayList<Kanji> array = new ArrayList<Kanji>();
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
            b.remember = Utils.getCursorString(c,Columns.REMEMBER);
            b.favorite = Utils.getCursorInt(c, Columns.FAVORITE);
            array.add(b);
            c.moveToNext();
        }
        c.close();

        return array;
    }


    public static ArrayList<Kanji> getAllFavorite(Context context) {
        DatabaseHandler db = DatabaseHandler.getDatabase(context);
        String strFilter = Columns.FAVORITE+ "=1";
        Cursor c = db.query(TABLE_NAME, null, strFilter, null, DEFAULT_SORT_ORDER);
        if (c == null) {
            return null;
        }
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();

        ArrayList<Kanji> array = new ArrayList<Kanji>();
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
            b.remember = Utils.getCursorString(c,Columns.REMEMBER);
            b.favorite = Utils.getCursorInt(c, Columns.FAVORITE);
            array.add(b);
            c.moveToNext();
        }
        c.close();

        return array;
    }

    public static ArrayList<Kanji> searchKanji(Context context, String searchKey) {
        DatabaseHandler db = DatabaseHandler.getDatabase(context);
        String selection="";
        if(Utils.isJapanese(searchKey.charAt(0))){
            char[] chars= searchKey.toCharArray();
            for(int i = 0; i <  chars.length; i++){
                if(i == chars.length - 1){
                    selection += Columns.KUNYOMI + " LIKE '%" + chars[i]  + "%'   OR " + Columns.ONYOMI + " LIKE '%" + chars[i]  + "%'   OR " + Columns.KANJI + " LIKE '%" + chars[i] + "%' ";
                }else{
                    selection += Columns.KUNYOMI + " LIKE '%" + chars[i]  + "%'   OR " + Columns.ONYOMI + " LIKE '%" + chars[i]  + "%'   OR " + Columns.KANJI + " LIKE '%" + chars[i] + "%' OR ";
                }

            }
        }else{
            selection += Columns.NAME + " LIKE '%" + searchKey  + "%'   OR " + Columns.MEAN_VIETNAMESE + " LIKE '%" + searchKey  + "%'   OR " + Columns.MEAN_ENGLISH + " LIKE '%" + searchKey + "%' ";
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
            b.remember = Utils.getCursorString(c,Columns.REMEMBER);
            b.favorite = Utils.getCursorInt(c, Columns.FAVORITE);
            areaArray.add(b);
            c.moveToNext();
        }
        c.close();

        return areaArray;
    }

    public static boolean updateFavorite(Context context,
                                         Kanji kanji) {
        DatabaseHandler db = DatabaseHandler.getDatabase(context);
        String strFilter = Columns.ID+ "=" + kanji.id;
        ContentValues cv = new ContentValues();
        cv.put(Columns.FAVORITE, kanji.favorite);
        db.update(TABLE_NAME, cv, strFilter, null);
        return true;
    }



    private static boolean isExist(Context context, Kanji diary) {
        // TODO Auto-generated method stub
        return false;
    }
}
