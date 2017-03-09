package com.giyer.noogleplatform.base;

/**
 * Created by giyer7 on 3/8/17.
 */

public class ResponseMessage {

    public static transient long DEFAULT_TIMESTAMP = -1;
    transient long mTimestamp = DEFAULT_TIMESTAMP;
    private transient int mOperationId;
    private transient String requestorName;
    private transient String cacheID;

    private transient boolean isCached;

    private transient int responseCode;


    private String jsonString;

    /**
     * Helper function for copying request fields into response
     * @param req
     */
    public void copyLocalRequestAttributes(RequestMessage req) {
        mOperationId = req.getOperationId();
        requestorName = req.getRequestorName();
        cacheID = req.getCacheID();
    }

    /**
     * (Optional) Id assoicated with this one time specific call.
     */
    public int getOperationId() {
        return mOperationId;
    }

    /**
     * (Optional) String name of class or object requesting this api
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * (Optional) Unique ID per API used for caching the data
     */
    public String getCacheID() {
        return cacheID;
    }

    public void setCached(boolean cached){
        isCached = cached;
    }

    public boolean isCached(){
        return isCached;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
