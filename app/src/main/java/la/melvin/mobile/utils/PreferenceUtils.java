package la.melvin.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static final String TAG = PreferenceUtils.class.getSimpleName();

    public static final String LAST_RATING_VALUE = "last_rating_value";
    public static final String CURRENT_USER = "current_user";

    public static SharedPreferences getPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences.Editor getEditor(Context ctx) {
        return getPreferences(ctx).edit();
    }

    /*
     * getKey overload
     */

    public static int getKey(Context ctx, String key, int defaultValue) {
        return getPreferences(ctx).getInt(key, defaultValue);
    }

    public static String getKey(Context ctx, String key, String defaultValue) {
        return getPreferences(ctx).getString(key, defaultValue);
    }

    public static boolean getKey(Context ctx, String key, boolean defaultValue) {
        return getPreferences(ctx).getBoolean(key, defaultValue);
    }

    public static long getKey(Context ctx, String key, long defaultValue) {
        return getPreferences(ctx).getLong(key, defaultValue);
    }

    public static float getKey(Context ctx, String key, float defaultValue) {
        return getPreferences(ctx).getFloat(key, defaultValue);
    }

    /*
     * putKey overload
     */

    public static void putKey(Context ctx, String key, int value) {
        getEditor(ctx).putInt(key, value).apply();
    }

    public static void putKey(Context ctx, String key, String value) {
        getEditor(ctx).putString(key, value).apply();
    }

    public static void putKey(Context ctx, String key, boolean value) {
        getEditor(ctx).putBoolean(key, value).apply();
    }

    public static void putKey(Context ctx, String key, long value) {
        getEditor(ctx).putLong(key, value).apply();
    }

    public static void putKey(Context ctx, String key, float value) {
        getEditor(ctx).putFloat(key, value).apply();
    }

    public static void removeKey(Context ctx, String key) {
        getEditor(ctx).remove(key).apply();
    }
}
