package com.giyer.noogle.network;

import com.giyer.noogle.network.base.BaseManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by giyer7 on 3/8/17.
 */

@Singleton
@Component(modules = {PlatformModule.class, PlatformAppModule.class})
public interface PlatformComponent {
    void inject(NoogleService service);
    void inject(BaseManager executor);
}