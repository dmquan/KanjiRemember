package com.supermario.kanjilookandlearn.common;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;


public class Utils {
    public static void longLog(String tag, String str) {
        if (str.length() > 4000) {
            Log.d(tag, str.substring(0, 4000));
            longLog(tag, str.substring(4000));
        } else
            Log.d(tag, str);
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    public static String getCursorString(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return c.getString(index);
        }
        return null;
    }

    public static int getCursorInt(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return c.getInt(index);
        }
        return -1;
    }

    public static long getCursorLong(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return c.getLong(index);
        }
        return -1;
    }

    public static float getCursorFloat(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return c.getFloat(index);
        }
        return -1;
    }

    public static String encodeUrl(String str) {
        String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        String urlEncoded = Uri.encode(str, ALLOWED_URI_CHARS);
        return urlEncoded;
    }


}