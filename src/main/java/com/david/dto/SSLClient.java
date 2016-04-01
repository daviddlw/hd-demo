package com.eif.paycorefrontedge.util.common;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

/**
 * Created by wenjing on 2016/1/30.
 */
public class SSLClient extends DefaultHttpClient {

    private final static Logger logger = Logger.getLogger(Logger.class);

    public SSLClient(ClientConnectionManager conman, HttpParams params, SSLCertAuthConfig config) throws NoSuchAlgorithmException, KeyManagementException{
        super(conman, params);
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustMangerImpl(config);
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }


}
