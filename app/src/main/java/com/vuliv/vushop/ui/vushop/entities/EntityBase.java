package com.vuliv.vushop.ui.vushop.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MB0000004 on 09-Jan-18.
 */

public class EntityBase {

    @SerializedName("model")
    String model = new String();

    @SerializedName("_interface")
    String Interface = new String();

    @SerializedName("uid")
    String uid = new String();

    @SerializedName("versionCode")
    int versionCode;

    @SerializedName("version")
    String version = new String();

    @SerializedName("deviceId")
    String deviceId;

//    @SerializedName("androidId")
//    String androidId = TweApplication.getInstance().getStartupFeatures().getDeviceInfo().getAndroidID();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInterface() {
        return Interface;
    }

    public void setInterface(String mInterface) {
        this.Interface = mInterface;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

}
