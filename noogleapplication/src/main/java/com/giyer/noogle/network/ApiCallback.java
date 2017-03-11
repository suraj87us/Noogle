package com.giyer.noogle.network;

import com.giyer.noogle.network.base.RequestMessage;
import com.giyer.noogle.network.base.ResponseMessage;

/**
 * Created by giyer7 on 3/8/17.
 */

public interface ApiCallback{
    void onResponse(RequestMessage requestMessage, ResponseMessage responseMessage, int httpResponseCode);

    void onFailure(RequestMessage requestMessage, int errorCode, String userMessage, String devMessage);
    void onExecutorFailure(Exception e);
}