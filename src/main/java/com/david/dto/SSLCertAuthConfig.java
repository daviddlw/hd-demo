package com.david.demo;

/**
 * Created by wenjing on 2016/1/30.
 */
public class SSLCertAuthConfig {

    private String subject;

    private String issuer;

    private String thumbPrint;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getThumbPrint() {
        return thumbPrint;
    }

    public void setThumbPrint(String thumbPrint) {
        this.thumbPrint = thumbPrint;
    }
}
