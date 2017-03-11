package com.giyer.noogle.network;

import android.app.Application;

/**
 * Created by giyer7 on 3/8/17.
 */

public class Platform {

    public static Platform instance = new Platform();
    private PlatformComponent component;

    public static void init(Application application){
        instance.component = DaggerPlatformComponent.builder()
                .platformAppModule(new PlatformAppModule(application))
                .platformModule(new PlatformModule())
                .build();
    }

    public PlatformComponent getComponent() {
        return component;
    }
}