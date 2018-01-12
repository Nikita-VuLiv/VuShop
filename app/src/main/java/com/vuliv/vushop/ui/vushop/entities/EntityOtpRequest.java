package com.vuliv.vushop.ui.vushop.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MB0000004 on 09-Jan-18.
 */

public class EntityOtpRequest extends EntityBase {

    @SerializedName("msisdn")
    String msisdn = new String();

    @SerializedName("resend")
    boolean resend;

    @SerializedName("buy")
    boolean buy;

    @SerializedName("registration")
    boolean registration;

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isResend() {
        return resend;
    }

    public void setResend(boolean resend) {
        this.resend = resend;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }
}
