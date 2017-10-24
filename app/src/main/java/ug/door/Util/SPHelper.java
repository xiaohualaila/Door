package ug.door.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by xyuxiao on 2016/9/23.
 */
public class SPHelper {
    private static String NAME = "config";

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static void save(Context context, String key, String value) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.putString(key, value);
        sharedata.commit();
    }

    public static void save(Context context, String key, boolean value) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.putBoolean(key, value);
        sharedata.commit();
    }

    public static void save(Context context, String key, float value) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.putFloat(key, value);
        sharedata.commit();
    }

    public static void save(Context context, String key, int value) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.putInt(key, value);
        sharedata.commit();
    }

    public static void save(Context context, String key, long value) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.putLong(key, value);
        sharedata.commit();
    }

    public static boolean getBoolean(Context context, String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getBoolean(key, defValue);
    }

    public static float getFloat(Context context, String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getFloat(key, defValue);
    }

    public static int getInt(Context context, String key, Integer defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getInt(key, defValue);
    }

    public static long getLong(Context context, String key, Long defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getLong(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getString(key, defValue);
    }

    public static void removeKey(Context context, String key) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.remove(key);
        sharedata.commit();
    }

    /**
     * 清空SharedPreferences
     *
     * @param context
     */
    public static void clearData(Context context) {
        Editor sharedata = getSharedPreference(context).edit();
        sharedata.clear().commit();
    }
}
