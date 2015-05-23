package com.fiubapp.fiubapp.dominio;

/**
 * Created by Gustavo.Franco on 22/05/2015.
 */
public class Discussion {
    private String discussionName;
    private String creatorUserName;
    private String creationDate;

    public String getDiscussionName() {
        return discussionName;
    }

    public void setDiscussionName(String discussionName) {
        this.discussionName = discussionName;
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
