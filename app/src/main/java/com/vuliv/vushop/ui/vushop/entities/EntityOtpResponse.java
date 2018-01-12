package com.vuliv.vushop.ui.vushop.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MB0000004 on 09-Jan-18.
 */

public class EntityOtpResponse extends EntityResponse {

    @SerializedName("otp")
    String otp = new String();

    @SerializedName("txnId")
    String txnId;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
