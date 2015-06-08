package com.fiubapp.fiubapp.dominio;

/**
 * Created by Gustavo.Franco on 22/05/2015.
 */
public class Message {


    private String text;
    private String creatorUserName;
    private String creationDate;
    private boolean hasAttachedFile;
    private String attachedFile;
    private String fileName;


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

    public boolean isHasAttachedFile() {
        return hasAttachedFile;
    }

    public void setHasAttachedFile(boolean hasAttachedFile) {
        this.hasAttachedFile = hasAttachedFile;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
