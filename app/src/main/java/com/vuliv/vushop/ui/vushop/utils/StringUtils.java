package com.vuliv.vushop.ui.vushop.utils;

/**
 * Created by MB0000004 on 04-Jan-18.
 */

public class StringUtils {

    /**
     * Checks whether the passed string is having some text or just a null string.
     * @param text
     * @return
     */
    public static boolean isEmpty(String text){
        boolean isEmpty = true;
        if(text != null && text.trim().length() > 0 ){
            isEmpty = false;
        }
        return isEmpty;
    }

    public static boolean isResponseIsPage(String response) {
        boolean page = false;
        if (response != null && response.trim().length() > 0) {
            if (response.trim().startsWith("<html>") || response.trim().startsWith("<HTML>")
                    || response.trim().startsWith("<!DOCTYPE") || response.trim().endsWith("<HTML>")
                    || response.trim().endsWith("<html>")) {
                page = true;
            }
        } else {
            page = true;
        }
        return page;
    }
}
