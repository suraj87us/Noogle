package com.giyer.noogle.network;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by giyer7 on 3/8/17.
 */
@Module
public class PlatformAppModule {
    private Application mApplication;

    public PlatformAppModule(Application application){
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }
}