package com.vuliv.vushop.ui.vushop.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MB0000004 on 09-Jan-18.
 */
public class EntityResponse extends EntityError {

    @SerializedName("model")
    String model = new String();

    @SerializedName("_interface")
    String mInterface = new String();

    @SerializedName("req_type")
    String reqType = new String();

    @SerializedName("uid")
    String uid = new String();

    @SerializedName("message")
    String message = new String();

    @SerializedName("status")
    String status = new String();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getmInterface() {
        return mInterface;
    }

    public void setmInterface(String mInterface) {
        this.mInterface = mInterface;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
