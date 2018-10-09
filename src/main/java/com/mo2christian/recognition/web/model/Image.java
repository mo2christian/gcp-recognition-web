package com.mo2christian.recognition.web.model;

/**
 * Represente l'image à analyser
 */
public class Image {

    private String bucketName;

    private String objectName;

    private String url;

    public Image(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
