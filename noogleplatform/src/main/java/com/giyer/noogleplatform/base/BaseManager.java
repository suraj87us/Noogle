package com.giyer.noogleplatform.base;

import com.giyer.noogleplatform.Platform;
import com.giyer.noogleplatform.managers.logger.Logger;

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
