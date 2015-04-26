package ar.uba.fi.fiubappMobile.notifications;

import com.fiubapp.fiubapp.Alumno;

/**
 * Created by marcelo on 4/19/15.
 */
public class ApplicationNotification extends Notification {

    private Alumno applicant;

    public Alumno getApplicant() {
        return applicant;
    }

    public void setApplicant(Alumno applicant) {
        this.applicant = applicant;
    }
}
