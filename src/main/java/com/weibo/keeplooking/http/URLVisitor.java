package com.weibo.keeplooking.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

/**
 * Demo for httpclient(4.3.4) usage
 *
 * @author Johnny
 */
@SuppressWarnings("deprecation")
public class URLVisitor {

    private HttpClient httpClient;

    public URLVisitor() throws Exception {
        ConnectionSocketFactory plainSF = PlainConnectionSocketFactory
                .getSocketFactory();

        SSLContext sslContext = SSLContexts.custom().useSSL()
                .setSecureRandom(new SecureRandom())
                .loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                        return true;
                    }
                }).build();

        ConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("http", plainSF)
                .register("https", sslSF).build();
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                registry);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * Make a HTTP(s) request to the link and return the page content.
     *
     * @param url
     *        link to visit, support both http & https
     * @return page content
     */
    public String visit(String url) {
        String result = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            result = httpClient.execute(httpGet, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity, "UTF-8");
                    } else {
                        throw new IOException("Null Entity!");
                    }
                }
            });
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new URLVisitor()
                .visit("http://blog.csdn.net/keeplook"));
    }

}
