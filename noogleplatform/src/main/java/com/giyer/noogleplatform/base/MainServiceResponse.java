package com.giyer.noogleplatform.base;

/**
 * Created by giyer7 on 3/8/17.
 */

public abstract class MainServiceResponse extends ResponseMessage {
    String errorMessage = "";
    String debugMessage = "";
    int errorCode = 0;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
