package com.weibo.keeplooking.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.junit.Test;

public class HttpClient3SSL {
    @Test
    public void testSSLConfig() throws Exception {
        HttpClient client = new HttpClient();
        String baseUrl = "https://expired.badssl.com";
        ProtocolSocketFactory factory = new FakeSSLCertificateSocketFactory(baseUrl);

        Protocol myhttps = new Protocol("myhttps", factory, 443);
        Protocol.registerProtocol("myhttps", myhttps);

        GetMethod get = new GetMethod(baseUrl.replace("https://", "myhttps://"));
        System.out.println(client.executeMethod(get));
    }

}
