package com.giyer.noogleplatform.base;

import android.os.Parcel;

import com.giyer.noogleplatform.BuildConfig;

/**
 * Created by giyer7 on 3/8/17.
 */

public abstract class MainServiceRequest extends RequestMessage {

    protected MainServiceRequest() {
        super();
        getParams().put("token", BuildConfig.WEBHOSE_TOKEN);
    }

    public String getMediaType(){
        return "application/json; charset=utf-8";
    }

    public String getHost(){
        return BuildConfig.ENDPOINT;
    }

    protected MainServiceRequest(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

}
