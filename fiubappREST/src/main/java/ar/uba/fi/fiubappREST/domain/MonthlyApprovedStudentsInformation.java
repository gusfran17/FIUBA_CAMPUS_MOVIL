package ar.uba.fi.fiubappREST.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

@Entity
@Table(name = "monthly_approved_students")
public class MonthlyApprovedStudentsInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date monthYear;
	
	private int amountOfStudents;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getMonthYear() {
		return monthYear;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}

	public int getAmountOfStudents() {
		return amountOfStudents;
	}

	public void setAmountOfStudents(int amountOfStudents) {
		this.amountOfStudents = amountOfStudents;
	}
}
