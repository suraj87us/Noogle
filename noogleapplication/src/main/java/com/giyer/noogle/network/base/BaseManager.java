package com.giyer.noogle.network.base;

import com.giyer.noogle.network.Platform;
import com.giyer.noogle.network.managers.logger.Logger;

import javax.inject.Inject;

/**
 * Created by giyer7 on 3/8/17.
 */

public class BaseManager {

    @Inject
    protected Logger mLogger;

    public BaseManager(){
        Platform.instance.getComponent().inject(this);
    }
}
