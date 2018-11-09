package com.mo2christian.recognition.web.action;

import com.mo2christian.recognition.web.model.*;
import com.mo2christian.recognition.web.service.DetectService;
import com.mo2christian.recognition.web.service.StorageService;
import com.mo2christian.recognition.web.service.TranslateService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Action pour analyser les images
 */
public class DetectAction extends ActionSupport {

    private DetectService detectService;

    private StorageService storageService;

    private TranslateService translateService;

    private Input input;

    private Output output;

    private List<Language> langues;

    private String version;

    private String email;

    public DetectAction(){
        langues = new LinkedList<>();
        langues.add(new Language("en", "English"));
        langues.add(new Language("fr", "French"));
        langues.add(new Language("es", "Spanish"));
    }

    public Token getToken(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        Token token = (Token) session.getAttribute(Token.NAME);
        return token;
    }

    public String detect(){
        try {
            Token token = getToken();
            email = token.getEmail();

            Image img = storageService.upload(input.getImage());
            DetectRequest detectRequest = new DetectRequest();
            detectRequest.setBucketName(img.getBucketName());
            detectRequest.setObjectName(img.getObjectName());
            detectRequest.setTarget(input.getTarget());
            detectRequest.setJwt(token.getValue());
            DetectResponse detectResponse = detectService.detectLabel(detectRequest);

            TranslateRequest translateRequest = new TranslateRequest();
            translateRequest.setFrom(detectResponse.getLocale());
            translateRequest.setTarget(input.getTarget());
            translateRequest.setWords(detectResponse.getWords());
            translateRequest.setJwt(token.getValue());
            List<String> translations = translateService.translate(translateRequest);

            output = new Output();
            output.setWords(translations);
            output.setUrl(img.getUrl());
            return SUCCESS;
        }
        catch(IOException ex){
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String index(){
        Token token = getToken();
        email = token.getEmail();

        input = new Input();
        input.setTarget(ServletActionContext.getRequest().getLocale().getLanguage());
        output = null;
        return SUCCESS;
    }

    /**
     * Donnees en entrée
     */
    public static class Input{

        private File image;

        private String target;

        private String imageFileName;

        public Input(){
        }

        public File getImage() {
            return image;
        }

        public void setImage(File image) {
            this.image = image;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getImageFileName() {
            return imageFileName;
        }

        void setImageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
        }
    }

    /**
     * Resultat de la detection.
     */
    public static class Output{

        private List<String> words;

        private String url;

        public Output(){
        }

        public List<String> getWords() {
            return words;
        }

        public void setWords(List<String> words) {
            this.words = words;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void setDetectService(DetectService detectService) {
        this.detectService = detectService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void setTranslateService(TranslateService translateService) {
        this.translateService = translateService;
    }

    public List<Language> getLangues() {
        return langues;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }
}
