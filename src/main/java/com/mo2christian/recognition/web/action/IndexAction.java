package com.mo2christian.recognition.web.action;

import com.mo2christian.recognition.web.model.Language;
import com.opensymphony.xwork2.ActionSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * Action pour la page d'accueil.
 */
public class IndexAction extends ActionSupport {

    public IndexAction(){
    }

    public String about(){
        return SUCCESS;
    }

}
