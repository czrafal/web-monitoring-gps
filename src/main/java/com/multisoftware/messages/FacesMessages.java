package com.multisoftware.messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

public class FacesMessages {

    public FacesMessages() {}

    public void showMessage(String message) {
        if (message == null) {
            message = StringUtils.EMPTY;
        }
        FacesMessage msg = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showMessageWithParam(String message, String param) {
        if (StringUtils.isBlank(param)) {
            param = StringUtils.EMPTY;
        }
        FacesMessage msg = new FacesMessage(message, param);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}