package ar.uba.fi.fiubappREST.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Inheritance
@DiscriminatorColumn(name="type")
@Table(name="configuration", uniqueConstraints=@UniqueConstraint(columnNames={"userName", "type"}))
public abstract class Configuration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
			
	private Boolean isEnabled;
	
	@ManyToOne
	@JoinColumn(name="userName")
	private Student student;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@JsonIgnore
	public String getType() {
	    return this.getClass().getSimpleName();
	}
}
