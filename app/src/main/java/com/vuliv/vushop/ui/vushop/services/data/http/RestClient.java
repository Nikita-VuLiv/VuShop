package com.vuliv.vushop.ui.vushop.services.data.http;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.vuliv.vushop.ui.vushop.application.TweApplication;
import com.vuliv.vushop.ui.vushop.services.network.CustomSSLSocketFactory;
import com.vuliv.vushop.ui.vushop.services.parser.ParserService;
import com.vuliv.vushop.ui.vushop.utils.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class RestClient<T> {
    public static final String HTTPS_STRING = "https";
    private static final String TAG = RestClient.class.getCanonicalName();
    private ParserService mParserService;
    private static HashMap<String, URLConnection> urlConnectionHashMap;
    public static final String secretKey = "T%5t!n6@uT4";

    private RestClient() {
        mParserService = new ParserService();
    }

    private static RestClient instance = null;
    public static final int HTTP_TIMEOUT = 1000 * 30; // milliseconds

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        if (urlConnectionHashMap == null) {
            urlConnectionHashMap = new HashMap<>();
        }
        return instance;
    }

    public String getRequest(String url, String applicationVersion) {
        Log.v(TAG, "URL : " + url);
        StringBuffer response = null;
        try {
            URL mUrl = new URL(url);
            URLConnection con;
            if (url.startsWith(HTTPS_STRING)) {
                con = (HttpsURLConnection) mUrl.openConnection();
            } else {
                con = (HttpURLConnection) mUrl.openConnection();
            }
//			con.setDoInput(true);
            con.setConnectTimeout(HTTP_TIMEOUT);
            con.setReadTimeout(HTTP_TIMEOUT);
            if (applicationVersion != null) {
                con.setRequestProperty("Content-Type", applicationVersion);
            }
//			con.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (con instanceof HttpsURLConnection) {
                Log.v(TAG, "STATUS CODE : " + ((HttpsURLConnection) con).getResponseCode());
                if (((HttpsURLConnection) con).getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    response = null;
                }
            } else if (con instanceof HttpURLConnection) {
                Log.v(TAG, "STATUS CODE : " + ((HttpURLConnection) con).getResponseCode());
                if (((HttpURLConnection) con).getResponseCode() != HttpURLConnection.HTTP_OK) {
                    response = null;
                }
            }
            if (con instanceof HttpsURLConnection) {
                ((HttpsURLConnection) con).disconnect();
            } else if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "Error : " + e.getMessage());
        }
        if (response != null && StringUtils.isResponseIsPage(response.toString())) {
            response = null;
        }
        return (response != null) ? response.toString() : null;
    }

    public String postRequest(String url, String jsonString, String applicationVersion) {

        StringBuffer response = null;
        try {
            URL mUrl = new URL(url);
            URLConnection con;
            if (url.startsWith(HTTPS_STRING)) {
                con = (HttpsURLConnection) mUrl.openConnection();
                ((HttpsURLConnection) con).setRequestMethod("POST");
                ((HttpsURLConnection) con).setSSLSocketFactory(CustomSSLSocketFactory.getInstance(TweApplication.getInstance()).getPinnedSSLSocketFactory());
            } else {
                con = (HttpURLConnection) mUrl.openConnection();
                ((HttpURLConnection) con).setRequestMethod("POST");
            }
            urlConnectionHashMap.put(url, con);
            con.setRequestProperty("Content-Type", applicationVersion);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", "vuliv_mp_an " + System.getProperty("http.agent"));
            con.setRequestProperty("X-FORWARDED-FOR", getLocalIpAddress(TweApplication.getInstance()));
            con.setRequestProperty("checksum", /*"7c93e5ad540e09bfac10eca18c2f36bc"*/getMD5());
            con.setDoInput(true);
            con.setConnectTimeout(HTTP_TIMEOUT);
            con.setReadTimeout(HTTP_TIMEOUT);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(jsonString);
            writer.close();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (con instanceof HttpsURLConnection) {
                if (((HttpsURLConnection) con).getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    response = null;
                }
            } else if (con instanceof HttpURLConnection) {
                if (((HttpURLConnection) con).getResponseCode() != HttpURLConnection.HTTP_OK) {
                    response = null;
                }
            }
            if (con instanceof HttpsURLConnection) {
                ((HttpsURLConnection) con).disconnect();
            } else if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnectionHashMap.remove(url);
            if (response != null && StringUtils.isResponseIsPage(response.toString())) {
                response = null;
            }
        }
        return (response != null) ? response.toString() : null;
    }

    public static String getLocalIpAddress(Context context) {
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

    public static String getMD5() {
//        String secretKey = "21424213";
//        String secretKey = "";
        String message = String.valueOf(getIMEI())+ "|" + "AN" + "|" + secretKey;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(message.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static String getIMEI() {
        String telephone = getTelephonyInstance().getDeviceId();
        return StringUtils.isEmpty(telephone) ? "0" : telephone;
    }

    private static TelephonyManager getTelephonyInstance() {
        TelephonyManager mTelephonyManager = (TelephonyManager) TweApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager;
    }
}
