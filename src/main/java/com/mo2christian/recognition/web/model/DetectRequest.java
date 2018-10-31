package com.mo2christian.recognition.web.model;

/**
 * Object encapsulant la requete de detection.
 */
public class DetectRequest {

    private String bucketName;

    private String objectName;

    private String target;

    private String jwt;

    public DetectRequest(){
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "DetectRequest{" +
                "objectName='" + objectName + '\'' +
                '}';
    }
}
