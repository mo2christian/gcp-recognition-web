package com.mo2christian.recognition.web.model;

import java.util.LinkedList;
import java.util.List;

public class TranslateRequest {

    private String from;

    private String target;

    private List<String> words;

    private String jwt;

    public TranslateRequest(){
        words = new LinkedList<>();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
