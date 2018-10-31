package com.mo2christian.recognition.web.service;

import com.google.gson.Gson;
import com.mo2christian.recognition.web.model.TranslateRequest;
import com.mo2christian.recognition.web.model.TranslateResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de traduction.
 */
public class TranslateService{

    private Logger logger = LogManager.getLogger(TranslateService.class.getClass());

    private String translateURL;

    @PostConstruct
    public void init(){
        String translateURL = System.getenv("TRANSLATE_URL");
        if (translateURL != null){
            this.translateURL = translateURL;
        }
    }

    public void setDefaultTranslateUrl(String translateAPI) {
        this.translateURL = translateAPI;
    }

    public List<String> translate(TranslateRequest request){
        logger.debug("Traduction de {} a {} sur le lien {}", request.getFrom(), request.getTarget(), translateURL);
        TranslateResponse response;
        HttpPost httpPost = new HttpPost(translateURL);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + request.getJwt());

        try (CloseableHttpClient httpClient = getHttpClient()) {
            Gson g = new Gson();
            HttpEntity entity = new StringEntity(g.toJson(request));
            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode > 299 || statusCode < 200)
                throw new RuntimeException(httpResponse.getStatusLine().getReasonPhrase());
            String result = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            response = g.fromJson(result, TranslateResponse.class);
        }
        catch(IOException ex){
            logger.error(ex);
            throw new RuntimeException(ex);
        }
        return response.getWords();
    }

    private CloseableHttpClient getHttpClient(){
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try{
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();
            /*HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);*/
            return httpClient;
        }
        catch(NoSuchAlgorithmException|KeyStoreException|KeyManagementException ex){
            throw new RuntimeException(ex);
        }

    }


}
