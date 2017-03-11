package com.giyer.noogle.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by giyer7 on 3/11/17.
 */

public class TimeAndDateUtil {

    private static final String TAG = TimeAndDateUtil.class.getSimpleName();
    // TODO Handle TimeZone changes.
    public static Time parseDateAndTime(String timestamp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date feedDate = dateFormat.parse(timestamp);
            Date today = new Date();

            long diff = today.getTime() - feedDate.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) (diff / (1000 * 60));
            int seconds = (int) (diff / (1000));
            return new Time(numOfDays,hours,minutes,seconds);
        } catch (ParseException e) {
            Log.d(TAG, "parseDateAndTime: ParseException while trying to parse "+ timestamp);
        }
        return null;
    }
}
