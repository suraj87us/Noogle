package com.giyer.noogle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by giyer7 on 3/9/17.
 */

public class Preferences {

    private static final String TAG = Preferences.class.getSimpleName();
    private SharedPreferences mPreferences;
    private String mBundleIdentifier;

    public Preferences(Application context) {
        mPreferences = context.getSharedPreferences("base_preferences", Context.MODE_PRIVATE);
        setBundleIdentifier(context.getPackageName());
    }

    public void setBundleIdentifier(String mBundleIdentifier) {
        this.mBundleIdentifier = mBundleIdentifier;
    }
}
