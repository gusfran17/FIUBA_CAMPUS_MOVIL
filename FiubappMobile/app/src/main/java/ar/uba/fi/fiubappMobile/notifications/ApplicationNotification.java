package ar.uba.fi.fiubappMobile.notifications;

/**
 * Created by marcelo on 4/19/15.
 */
public class ApplicationNotification extends Notification {

    private String applicantUserName;
    private String applicantName;
    private String applicantLastName;

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantLastName() {
        return applicantLastName;
    }

    public void setApplicantLastName(String applicantLastName) {
        this.applicantLastName = applicantLastName;
    }

}
