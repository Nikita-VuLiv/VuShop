package com.vuliv.vushop.ui.vushop.ui.controllers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.vuliv.vushop.ui.vushop.entities.EntityOtpRequest;
import com.vuliv.vushop.ui.vushop.entities.EntityOtpResponse;
import com.vuliv.vushop.ui.vushop.services.data.http.RestClient;
import com.vuliv.vushop.ui.vushop.ui.callbacks.IUniversalCallback;
import com.vuliv.vushop.ui.vushop.utils.StringUtils;

import java.util.Locale;

/**
 * Created by MB0000004 on 09-Jan-18.
 */

public class OtpRequestController {
    private Context context;

    public OtpRequestController(Context context) {
        this.context = context;
    }

    public void otpRequest(final IUniversalCallback<EntityOtpResponse, String> callback, final String msisdn, final boolean resend, boolean buy, boolean registration) {
            final String url = "http://52.76.81.227:8081/" + "theapp/webapi/generateotp";
            final String request = generateOTPRequest(msisdn, resend, buy, registration);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    callback.onPreExecute();
                    String response = RestClient.getInstance().postRequest(url, request, "application/v2+json");
                    EntityOtpResponse entity = null;
                    if (!StringUtils.isEmpty(response)) {
                        entity = parseOtpResponse(response);
                        if (entity.getStatus().equalsIgnoreCase("200")) {
                            callback.onSuccess(entity);
                        } else if (entity.getStatus().equalsIgnoreCase("101")) {
                            callback.onFailure(entity.getMessage());
                        } else {
                            callback.onFailure(entity.getMessage());
                        }
                    } else {
                        callback.onFailure("No internet connection found. \\nPlease try again.");
                    }
                }
            }).start();
    }

    private EntityOtpResponse parseOtpResponse(String json) {
        Gson gson = new Gson();
        json = json.replace("@Produces(\"com.vuliv.vushop.ui.vushop.application/json\")", "");
        EntityOtpResponse entity = gson.fromJson(json, EntityOtpResponse.class);
        return entity;
    }

    private String generateOTPRequest(String msisdn, boolean resend, boolean buy, boolean registration) {
        String json = null;
        try {
            Gson gson = new Gson();
            EntityOtpRequest entity = new EntityOtpRequest();
            entity.setBuy(buy);
            entity.setInterface("AN");
            entity.setModel(getDeviceModel());
            entity.setVersion(initAppInfoVersion());
            entity.setVersionCode(initAppInfoVersionCode());
            entity.setUid(String.valueOf(getIMEI()));
            entity.setDeviceId(String.valueOf(getIMEI()));
            entity.setMsisdn(msisdn);
            entity.setResend(resend);
            entity.setRegistration(registration);
            json = gson.toJson(entity, EntityOtpRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String manModel = "";
        if (model.startsWith(manufacturer)) {
            manModel = model.toUpperCase(Locale.getDefault());
        } else {
            manModel = manufacturer.toUpperCase() + "" + model.toUpperCase();
        }
        if (manModel.contains(" ")) {
            manModel = manModel.replaceAll(" ", "");
        }
        return manModel;
    }

    public String getIMEI() {
        String telephone = getTelephonyInstance().getDeviceId();
        return StringUtils.isEmpty(telephone) ? "0" : telephone;
    }

    private TelephonyManager getTelephonyInstance() {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager;
    }

    public String initAppInfoVersion() {
        String versionName = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    public int initAppInfoVersionCode() {
        int versionCode = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }
}
