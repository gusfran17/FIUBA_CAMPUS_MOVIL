package ar.uba.fi.fiubappREST.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

@Entity
@Table(name = "job")
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String company;
	
	private String position;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	@ManyToOne
	@JoinColumn(name="userName")
	private Student student;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public Student getStudent() {
		return student;
	}

	@JsonIgnore
	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
