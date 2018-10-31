package com.mo2christian.recognition.web.service;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Classe utilisèe pour valider une connexion SSL.
 */
public class SSLValidator {

    protected CloseableHttpClient getHttpClient(){
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try{
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();
            return httpClient;
        }
        catch(NoSuchAlgorithmException |KeyStoreException |KeyManagementException ex){
            throw new RuntimeException(ex);
        }
    }

    protected HttpComponentsClientHttpRequestFactory getHttpClientFactory(){
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(getHttpClient());
        return requestFactory;
    }

}
