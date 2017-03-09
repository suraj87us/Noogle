package com.giyer.noogleplatform;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.giyer.noogleplatform.base.RequestMessage;

/**
 * Created by giyer7 on 3/8/17.
 */

public class HttpUtil {
    /**
     * Will invoke the Service and make the API call.
     * This is the entry point from the Application module
     * @param context
     * @param message
     */
    public static void SendMessage(Context context, RequestMessage message) {
        Intent intent = new Intent(context, NoogleService.class);
        intent.putExtra(NoogleService.COMMAND_VALUE, NoogleService.NOOGLE_MESSAGE);
        intent.putExtra(NoogleService.MESSAGE_EXTRA, (Parcelable) message);
        context.startService(intent);
    }
}
