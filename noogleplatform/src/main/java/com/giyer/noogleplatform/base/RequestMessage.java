package com.giyer.noogleplatform.base;

/**
 * Created by giyer7 on 3/8/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.giyer.noogleplatform.APIEndpoints;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestMessage implements Parcelable {


    private transient String requestorName;

    private transient int mOperationId = 0;

    private transient Map<String, String> mExtraParams = new HashMap<String, String>();

    private transient String cacheID;


    private transient Long timestamp;

    private transient boolean isCached;

    public RequestMessage() {
    }

    public RequestMessage(Parcel in) {
        in.readMap(mExtraParams, HashMap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(mExtraParams);
    }

    /**
     * Unique ID per API used for caching the data
     *
     * @param setCacheId
     */
    protected RequestMessage(String setCacheId) {
        cacheID = setCacheId;
    }

    /**
     * Request method type (GET or POST or PUT)
     *
     * @return
     * @see APIEndpoints.RequestMethod
     */
    public abstract APIEndpoints.RequestMethod getRequestMethod();

    /**
     * MediaType parsed
     *
     * @return
     */
    public abstract String getMediaType();

    /**
     * API Path. EX: /query/cars
     *
     * @return
     */
    public abstract String getPath();

    /**
     * API Params
     *
     * @return
     */
    public Map<String, String> getParams() {
        return mExtraParams;
    }

    public void addParams(String key, String value) {
        mExtraParams.put(key, value);
    }
    /**
     * Class to unmarshal
     *
     * @return
     */
    public abstract Class<?> getResponseMessageClass();

    /**
     * To get the API Key
     *
     * @return
     */
    public abstract String getApiID();


    public String getCacheID() {
        return getApiID();
    }

    /**
     * Is this information cachable
     *
     * @return
     */
    public boolean isCachable() {
        return false;
    }

    /**
     * (Optional) Id assoicated with this one time specific call.
     */
    public int getOperationId() {
        return mOperationId;
    }

    /**
     * (Optional) Id assoicated with this one time specific call.
     */
    public void setOperationId(int mOperationId) {
        this.mOperationId = mOperationId;
    }

    /**
     * (Optional) String name of class or object requesting this api
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * (Optional) String name of class or object requesting this api
     */
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public boolean isCached() {
        return isCached;
    }

    /**
     * Get API Request headers
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        return null;
    }

    /**
     * Host URL. EX: www.google.com
     *
     * @return
     */
    public abstract String getHost();

    public void setTimestamp() {
        timestamp = System.currentTimeMillis() / 1000;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}

