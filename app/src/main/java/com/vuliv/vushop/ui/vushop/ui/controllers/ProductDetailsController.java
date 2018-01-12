package com.vuliv.vushop.ui.vushop.ui.controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.vuliv.vushop.ui.vushop.entities.EntityDominos;
import com.vuliv.vushop.ui.vushop.services.data.http.RestClient;
import com.vuliv.vushop.ui.vushop.ui.callbacks.IUniversalCallback;
import com.vuliv.vushop.ui.vushop.utils.StringUtils;

/**
 * Created by MB0000004 on 04-Jan-18.
 */

public class ProductDetailsController {

    private Context context;

    public ProductDetailsController(Context context) {
        this.context = context;
    }

    public void getProductDetails(final IUniversalCallback<EntityDominos, String> callback, final String url) {

        String response = RestClient.getInstance().getRequest(url, null);
        Gson gson = new Gson();
        if (!StringUtils.isEmpty(response)) {
            EntityDominos dominosResponse = gson.fromJson(response, EntityDominos.class);
            if(dominosResponse != null){

            }
        }
    }
}
