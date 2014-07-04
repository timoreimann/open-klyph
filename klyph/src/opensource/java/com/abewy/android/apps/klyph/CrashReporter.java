package com.abewy.android.apps.klyph;

import android.util.Log;

import java.lang.String;
import java.lang.StringBuilder;
import java.util.Map;
import java.util.TreeMap;

/**
 * CrashReporter via android log.
 */
public class CrashReporter {
    protected static String TAG = "KlyphCrash";
    protected static TreeMap<String, String> mapping = new TreeMap<String, String>();
    public static void setString(String key, String value) {
        synchronized (mapping) {
            mapping.put(key, value);
        }
        Log.d(TAG, "Setting " + key + "=" + value);
    }
    public static void logException(Exception exception) {
        StringBuilder sb = new StringBuilder();
        synchronized (mapping) {
            for (Map.Entry<String, String> e : mapping.entrySet()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(e.getKey() + "=" + e.getValue());
            }
        }
        if (sb.length() == 0) {
            sb.append("<no metadata>");
        }
        Log.e(TAG, sb.toString(), exception);
    }
}
