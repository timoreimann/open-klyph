package com.abewy.android.apps.openklyph;

import com.crashlytics.android.Crashlytics;

/**
 * CrashReporter for Crashlytics.
 */
public class CrashReporter {
    public static void setString(String key, String value) {
        Crashlytics.setString(key, value);
    }
    public static void logException(Exception e) {
        Crashlytics.logException(e);
    }
}
