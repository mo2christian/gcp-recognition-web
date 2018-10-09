package com.mo2christian.recognition.web.model;

/**
 * Object representant une langue.
 */
public class Language {

    private String code;

    private String text;

    public Language(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
