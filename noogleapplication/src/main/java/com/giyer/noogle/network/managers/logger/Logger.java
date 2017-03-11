package com.giyer.noogle.network.managers.logger;

/**
 * Created by giyer7 on 3/8/17.
 */

public interface Logger {
    final String TAG_TRAVELER = "Noogle ";

    void logException(String tag, Exception e);
    void logException(Exception e);
    void d(String message);
    void d(String tag, String message);
    void i(String message);
    void i(String tag, String message);
    void e(String message);
    void e(String tag, String message);
}
