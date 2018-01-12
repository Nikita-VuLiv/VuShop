package com.vuliv.vushop.ui.vushop.services.parser;

import com.google.gson.Gson;

/**
 * Created by MX0002112 on 17-11-2015.
 */
public class ParserService<T> {

    public ParserService() {

    }

    public T parseJson(String jsonString, Class classType) {
        if (jsonString.contains("@Produces(\"com.vuliv.vushop.ui.vushop.application/json\")")) {
            jsonString = jsonString.replace("@Produces(\"com.vuliv.vushop.ui.vushop.application/json\")", "");
        }
        Gson gson = new Gson();
        return (T) gson.fromJson(jsonString, classType);
    }

    public String convertToJsonString(Object object, Class classType){
        if(object == null) return "";
        Gson gson = new Gson();
        return gson.toJson(object,classType);
    }
}
