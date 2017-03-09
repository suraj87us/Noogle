package com.giyer.noogleplatform;

import android.app.Application;

import com.giyer.noogleplatform.managers.environment.EnvironmentManagerImpl;

/**
 * Created by giyer7 on 3/8/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Platform.instance.init(this);
        EnvironmentManagerImpl.getInstance().initialize(this);

        /**
         * In case you want to ensure exceptions are informed without user submission
         */
//        if(BuildConfig.BUILD_RELEASE) {
//            final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//            Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
//                defaultHandler.uncaughtException(thread, e);
//            });
//        }

    }
}
