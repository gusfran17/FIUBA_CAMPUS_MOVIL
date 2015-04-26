package ar.uba.fi.fiubappREST.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.StudentProfileSerializer;

@Entity
@DiscriminatorValue("APPLICATION_NOTIFICATION")
public class ApplicationNotification extends Notification {
	
	@OneToOne
	@JoinColumn(name="applicantUserName")
	private Student applicant;

	@JsonSerialize(using = StudentProfileSerializer.class)
	public Student getApplicant() {
		return applicant;
	}

	public void setApplicant(Student applicant) {
		this.applicant = applicant;
	}
	
}
