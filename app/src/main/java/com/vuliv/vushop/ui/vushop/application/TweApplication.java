package com.vuliv.vushop.ui.vushop.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by MB0000004 on 10-Jan-18.
 */

public class TweApplication extends Application {

    public static TweApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static TweApplication getInstance() {
        if (mInstance == null) {
            mInstance = new TweApplication();
        }
        return mInstance;
    }
}
