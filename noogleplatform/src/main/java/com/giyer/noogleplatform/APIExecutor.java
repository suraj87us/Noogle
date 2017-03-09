package com.giyer.noogleplatform;

import android.support.annotation.NonNull;

import com.giyer.noogleplatform.base.RequestMessage;

/**
 * Created by giyer7 on 3/8/17.
 */

public interface APIExecutor {

    void execute(@NonNull  final RequestMessage requestMessage, ApiCallback listener);
}
