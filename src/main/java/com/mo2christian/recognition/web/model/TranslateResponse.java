package com.mo2christian.recognition.web.model;

import java.util.LinkedList;
import java.util.List;

public class TranslateResponse {

    private List<String> words;

    TranslateResponse(){
        words = new LinkedList<>();
    }

    public List<String> getWords() {
        return words;
    }

    void setWords(List<String> words) {
        this.words = words;
    }

}
