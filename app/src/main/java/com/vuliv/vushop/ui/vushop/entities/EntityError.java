package com.vuliv.vushop.ui.vushop.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MB0000004 on 09-Jan-18.
 */

public class EntityError {

//	public NetworkErrorCodes getmNetworkErrorCodes() {
//		return mNetworkErrorCodes;
//	}
//
//	public void setmNetworkErrorCodes(NetworkErrorCodes mNetworkErrorCodes) {
//		this.mNetworkErrorCodes = mNetworkErrorCodes;
//	}

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(String errorAction) {
        this.errorAction = errorAction;
    }

//	private NetworkErrorCodes mNetworkErrorCodes;

    @SerializedName("error_code")
    String errorCode = new String();

    @SerializedName("error_message")
    String errorMessage = new String();

    @SerializedName("error_action")
    String errorAction = new String();
}
