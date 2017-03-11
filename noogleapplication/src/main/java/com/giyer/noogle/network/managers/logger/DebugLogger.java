package com.giyer.noogle.network.managers.logger;

import android.util.Log;

/**
 * Created by giyer7 on 3/8/17.
 */

public class DebugLogger implements Logger {
    @Override
    public void logException(String tag, Exception e) {
        e.printStackTrace();
        d(tag, e.getMessage());
    }

    @Override
    public void logException(Exception e) {
        logException("", e);
    }

    @Override
    public void d(String message) {
        d("", message);
    }

    @Override
    public void d(String tag, String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(tag, message.substring(start, end));
        }
    }

    @Override
    public void i(String message) {
        e("", message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(TAG_TRAVELER+tag, message);
    }

    @Override
    public void e(String message) {
        e("", message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(TAG_TRAVELER+tag, message);
    }
}
