package com.vuliv.vushop.ui.vushop.ui.callbacks;

/**
 * Created by MB0000004 on 04-Jan-18.
 */

public interface IUniversalCallback<T,U> {
        void onPreExecute();
        void onFailure(U msg);
        void onSuccess(T object);
    }
