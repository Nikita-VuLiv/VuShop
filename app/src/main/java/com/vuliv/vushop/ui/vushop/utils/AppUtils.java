package com.vuliv.vushop.ui.vushop.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by MB0000004 on 10-Jan-18.
 */

public class AppUtils {

    public static void runOnUiThread(Runnable runnable) {

        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }
}
