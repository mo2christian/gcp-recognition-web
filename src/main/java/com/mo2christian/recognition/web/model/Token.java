package com.mo2christian.recognition.web.model;

/**
 * Classe contenant le token kwt en cours d'utilisation.
 */
public class Token {

    public static final String NAME = "token";

    private String value;

    private String email;

    public Token() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
