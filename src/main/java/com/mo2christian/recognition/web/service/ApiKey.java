package com.mo2christian.recognition.web.service;

/**
 * Keys to call APIs
 */
public interface ApiKey {

    default String getKey(){
        return System.getenv("API_KEY");
    }

    default String addKey(String host){
        if (host.contains("?")){
            return String.format("%s&key=%s", host, getKey());
        }
        else{
            return String.format("%s?key=%s", host, getKey());
        }
    }

}
