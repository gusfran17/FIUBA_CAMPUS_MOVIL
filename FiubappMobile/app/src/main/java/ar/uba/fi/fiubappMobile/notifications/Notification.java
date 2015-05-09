package ar.uba.fi.fiubappMobile.notifications;

import java.util.Date;

public class Notification {

    private Integer id;
    private Date creationDate;
    private Boolean isViewed;
    private String type;

    public Notification() {

    }

    public Notification(Integer id, Date creationDate, Boolean isViewed, String type) {
        this.id = id;
        this.creationDate = creationDate;
        this.isViewed = isViewed;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Boolean isViewed) {
        this.isViewed = isViewed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}