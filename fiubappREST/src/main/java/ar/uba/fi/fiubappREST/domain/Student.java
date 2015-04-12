package ar.uba.fi.fiubappREST.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

@Entity
@Table(name = "student")
public class Student {

	@Id
	private String userName;
	
	private String password;
	
	private String name;
	
	private String lastName;

	private Boolean isExchangeStudent;
		
	private String passportNumber;
	
	private String fileNumber;
	
	private String email;
	
	private Date dateOfBirth;
	
	private String phoneNumber;
	
	private String currentCity;
	
	private String nationality;
	
	private String comments;
	
	@Enumerated
	private Gender gender;

	@OneToMany(mappedBy="student", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private List<StudentCareer> careers;
	
	@OneToOne(mappedBy = "student", cascade={CascadeType.ALL}, orphanRemoval = true)
	private HighSchool highSchool;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getIsExchangeStudent() {
		return isExchangeStudent;
	}

	public void setIsExchangeStudent(Boolean isExchangeStudent) {
		this.isExchangeStudent = isExchangeStudent;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<StudentCareer> getCareers() {
		return careers;
	}

	public void setCareers(List<StudentCareer> careers) {
		this.careers = careers;
	}
	
	@JsonIgnore
	public HighSchool getHighSchool() {
		return highSchool;
	}

	public void setHighSchool(HighSchool highSchool) {
		this.highSchool = highSchool;
	}

	public void addCareer(final StudentCareer career) {
		if(this.existsCareer(career.getCareer())){
			throw new CareerAlreadyExistsForStudentException(this.userName, career.getCareer().getCode());
		}
		career.setStudent(this);
		this.careers.add(career);
	}
	
	private boolean existsCareer(final Career career){
		StudentCareer foundCareer = (StudentCareer) CollectionUtils.find(this.careers, new Predicate() {
            public boolean evaluate(Object object) {
                return ((StudentCareer) object).getCareer().getCode().equals(career.getCode());
            }
		});
		return foundCareer!=null;
	}
}
