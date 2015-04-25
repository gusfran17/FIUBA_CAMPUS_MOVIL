package ar.uba.fi.fiubappREST.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.StudentCareerSerializer;

@Entity
@Table(name = "student_career", uniqueConstraints=@UniqueConstraint(columnNames={"code", "userName"}))
@JsonSerialize(using = StudentCareerSerializer.class)
public class StudentCareer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
 	@OneToOne
 	@JoinColumn(name="code")
	private Career career;
	
	@ManyToOne
	@JoinColumn(name="userName")
	private Student student;

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		StudentCareer other = (StudentCareer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
