package com.giyer.noogleplatform;

import com.giyer.noogleplatform.base.BaseManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by giyer7 on 3/8/17.
 */

@Singleton
@Component(modules = {PlatformModule.class, AppModule.class})
public interface PlatformComponent {
    void inject(NoogleService service);
    void inject(BaseManager executor);
}