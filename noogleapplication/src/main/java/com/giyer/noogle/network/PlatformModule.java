package com.giyer.noogle.network;

import com.giyer.noogle.network.managers.encryption.Encryption;
import com.giyer.noogle.network.managers.encryption.EncryptionImpl;
import com.giyer.noogle.network.managers.logger.DebugLogger;
import com.giyer.noogle.network.managers.logger.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by giyer7 on 3/8/17.
 */

@Module
public class PlatformModule {

    @Provides
    @Singleton
    Logger provideLogger(){
        return new DebugLogger();
    }

    @Provides
    @Singleton
    APIExecutor provideAPIExecutor(){
        return new OkHttpExecutor();
    }

    @Provides
    @Singleton
    Encryption providesEncryption(){
        return new EncryptionImpl();
    }
}
