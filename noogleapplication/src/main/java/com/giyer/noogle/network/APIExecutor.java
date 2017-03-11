package com.giyer.noogle.network;

import android.support.annotation.NonNull;

import com.giyer.noogle.network.base.RequestMessage;

/**
 * Created by giyer7 on 3/8/17.
 */

public interface APIExecutor {

    void execute(@NonNull  final RequestMessage requestMessage, ApiCallback listener);
}
