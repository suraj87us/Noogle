package com.giyer.noogle.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.giyer.noogle.network.base.RequestMessage;
import com.giyer.noogle.network.base.ResponseMessage;
import com.giyer.noogle.network.managers.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by giyer7 on 3/8/17.
 */

public class NoogleService extends Service implements ApiCallback {

    public static String COMMAND_VALUE = "CommandValue";
    public static final int NOOGLE_MESSAGE = 0;
    public static final String MESSAGE_EXTRA = "MESSAGE_EXTRAS";

    @Inject
    APIExecutor mAPIExecutor;
    @Inject
    Logger mLogger;


    public NoogleService() {
        Platform.instance.getComponent().inject(this);
    }


    public int detachFrom(Intent intent) {
        return intent.getIntExtra(COMMAND_VALUE, -1);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || detachFrom(intent) == -1) {
            mLogger.d(NoogleService.class.getSimpleName(), "onStartCommand(): is NULL");
            return Service.START_NOT_STICKY;
        }
        if (detachFrom(intent) == NOOGLE_MESSAGE) {
            RequestMessage requestMessage = getPayload(intent);

            requestMessage.setTimestamp();
            mAPIExecutor.execute(requestMessage, this);

            return Service.START_NOT_STICKY;
        }
        mLogger.d(NoogleService.class.getSimpleName(), "API Call Start: " + System.currentTimeMillis());
        return Service.START_NOT_STICKY;
    }

    private RequestMessage getPayload(Intent intent) {
        return (RequestMessage) intent.getExtras().getParcelable(MESSAGE_EXTRA);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onResponse(RequestMessage requestMessage, ResponseMessage responseMessage, int httpCode) {

        responseMessage.setResponseCode(httpCode);
        EventBus.getDefault().post(responseMessage);
        mLogger.d(NoogleService.class.getSimpleName(), "<<on response>> " + responseMessage.toString());
    }

    @Override
    public void onFailure(RequestMessage requestMessage, int errorCode, String userMessage, String devMessage) {
        mLogger.d(NoogleService.class.getSimpleName(), "Command that Failed: " + requestMessage.getClass().toString());
    }

    @Override
    public void onExecutorFailure(Exception e) {
        EventBus.getDefault().post(e);
    }
}
