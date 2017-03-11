package com.giyer.noogle;

import com.giyer.noogle.base.BaseActivity;
import com.giyer.noogle.network.PlatformAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by giyer7 on 3/9/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, PlatformAppModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
}