package com.giyer.noogle;

import android.app.Application;

import com.giyer.noogle.network.managers.logger.DebugLogger;
import com.giyer.noogle.network.managers.logger.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by giyer7 on 3/9/17.
 */

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Preferences providesPreferences(Application application){
        return new Preferences(application);
    }

    @Provides
    @Singleton
    Logger providesLogger(){
        return new DebugLogger();
    }
}