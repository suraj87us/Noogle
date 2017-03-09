package com.giyer.noogle.util;

import android.util.Log;

/**
 * Created by giyer7 on 3/8/17.
 */

public class LogUtil {
    private static final String LOG_PREFIX = "NOOGLE";

    private static final boolean LOGGING_ENABLED = true;
    private static final int MAX_TAG_LENGTH = 23;
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();

    public static String makeLogTag(Class clazz) {
        return makeLogTag(clazz.getSimpleName());
    }


    public static String makeLogTag(String str) {
        if (str.length() > MAX_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }


    public static void logD(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message);
            }
        }
    }

    public static void logD(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message, cause);
            }
        }
    }
}
