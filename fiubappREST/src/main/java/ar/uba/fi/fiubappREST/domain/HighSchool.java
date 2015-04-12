package ar.uba.fi.fiubappREST.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

@Entity
@Table(name = "high_school", uniqueConstraints=@UniqueConstraint(columnNames={"userName"}))
public class HighSchool {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String degree;
	
	private String schoolName;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	@OneToOne
	@JoinColumn(name="userName")
	private Student student;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDateFrom() {
		return dateFrom;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDateTo() {
		return dateTo;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}
