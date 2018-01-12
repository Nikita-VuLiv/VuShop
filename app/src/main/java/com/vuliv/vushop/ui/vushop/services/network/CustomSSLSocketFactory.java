package com.vuliv.vushop.ui.vushop.services.network;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by MB0000004 on 10-Jan-18.
 */

public class CustomSSLSocketFactory {

    private static Context context;
    private static String publicKey;
    private static CustomSSLSocketFactory instance;
    private static SSLSocketFactory pinnedSSLSocketFactory;

    public static CustomSSLSocketFactory getInstance(Context context) {
        if (instance == null && pinnedSSLSocketFactory == null) {
            CustomSSLSocketFactory.context = context;
            instance = new CustomSSLSocketFactory();
            setSSLSocket();
        }
        return instance;
    }

    private static void setSSLSocket () {
        AssetManager assManager = context.getAssets();
        InputStream is = null;
        try {
            is = assManager.open("certificate.crt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PublicKey pk = null;
        try {
            InputStream caInput = new BufferedInputStream(is);
            CertificateFactory f = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate)f.generateCertificate(caInput);
            pk = certificate.getPublicKey();
            publicKey = new BigInteger(1 /* positive */, pk.getEncoded()).toString(16);
        } catch (Exception e){
            e.printStackTrace();
        }
        TrustManager tm[] = {new PubKeyManager(publicKey)};
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tm, null);
            pinnedSSLSocketFactory = context.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public SSLSocketFactory getPinnedSSLSocketFactory() {
        return pinnedSSLSocketFactory;
    }
}
