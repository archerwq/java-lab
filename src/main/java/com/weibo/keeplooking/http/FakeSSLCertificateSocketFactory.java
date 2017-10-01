/*
 * Copyright � 2009 VMware, Inc. All rights reserved. Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE. Except as explicitly provided herein, no express or
 * implied licenses, under any VMware patents, copyrights, trademarks, or any
 * other intellectual property rights, are granted or waived by implication,
 * exhaustion, estoppel, or otherwise, on modified versions of the Software.
 */

package com.weibo.keeplooking.http;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Helper class to accept self-signed certificate.
 */
public class FakeSSLCertificateSocketFactory implements SecureProtocolSocketFactory {

    private final Logger log = LoggerFactory.getLogger(FakeSSLCertificateSocketFactory.class);
    private SSLContext sslContext;
    private X509TrustManager trustManager;
    private String baseUrl;

    public FakeSSLCertificateSocketFactory(String baseUrl) throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        trustManager = this.createTrustManager();
        sslContext.init(null, new TrustManager[] {trustManager}, null);
        this.baseUrl = baseUrl;
    }

    private X509TrustManager createTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
                // Allow.
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                log.info("in FakeSSLCertificateSocketFactory checkServerTrusted(): " + baseUrl);
            }


            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(s, i);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetaddress, int j) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(s, i, inetaddress, j);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetaddress, int j, HttpConnectionParams httpconnectionparams) throws IOException,
            UnknownHostException, ConnectTimeoutException {
        return sslContext.getSocketFactory().createSocket(s, i, inetaddress, j);
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, s, i, flag);
    }

}
