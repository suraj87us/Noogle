package com.giyer.noogle;

import android.support.multidex.MultiDexApplication;

import com.giyer.noogle.network.Platform;
import com.giyer.noogle.network.PlatformAppModule;
import com.giyer.noogle.network.managers.environment.EnvironmentManagerImpl;

/**
 * Created by giyer7 on 3/9/17.
 */

public class NoogleApplication extends MultiDexApplication {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Platform.init(this);
        EnvironmentManagerImpl.getInstance().initialize(this);

        mComponent = DaggerApplicationComponent.builder()
                .platformAppModule(new PlatformAppModule(this))
                .applicationModule(new ApplicationModule())
                .build();

    }

    public ApplicationComponent getComponent(){
        return mComponent;
    }
}
