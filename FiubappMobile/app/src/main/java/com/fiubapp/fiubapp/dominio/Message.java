package com.fiubapp.fiubapp.dominio;

/**
 * Created by Gustavo.Franco on 22/05/2015.
 */
public class Message {

    private String text;
    private String creatorUserName;
    private String creationDate;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}
