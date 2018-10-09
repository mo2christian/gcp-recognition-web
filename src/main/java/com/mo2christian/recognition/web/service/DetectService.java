package com.mo2christian.recognition.web.service;

import com.google.gson.Gson;
import com.mo2christian.recognition.web.model.DetectRequest;
import com.mo2christian.recognition.web.model.DetectResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Service pour detecter le contenu d'une image.
 */
public class DetectService {

    private String detectUrl;

    public DetectService(){
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public DetectResponse detectLabel(DetectRequest request){
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);
        ResponseEntity<String> resp = restTemplate.postForEntity(detectUrl, entity, String.class);
        HttpStatus status = resp.getStatusCode();
        if (!status.is2xxSuccessful())
            throw new RuntimeException(status.getReasonPhrase());
        return gson.fromJson(resp.getBody(), DetectResponse.class);
    }

}
