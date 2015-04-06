package ar.uba.fi.fiubappREST.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.codehaus.jackson.annotate.JsonIgnore;

import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsForStudent;

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

	@OneToMany(mappedBy="student", cascade={CascadeType.ALL})
	private List<StudentCareer> careers;
	
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

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

	public List<StudentCareer> getCareers() {
		return careers;
	}

	public void setCareers(List<StudentCareer> careers) {
		this.careers = careers;
	}
	
	public void addCareer(final StudentCareer career) {
		if(this.existsCareer(career.getCareer())){
			throw new CareerAlreadyExistsForStudent(this.userName, career.getCareer().getCode());
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
