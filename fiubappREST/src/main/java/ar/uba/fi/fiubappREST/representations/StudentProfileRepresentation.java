package ar.uba.fi.fiubappREST.representations;

import java.util.List;

public class StudentProfileRepresentation {

	private String userName;
		
	private String name;
	
	private String lastName;

	private Boolean isExchangeStudent;
		
	private String passportNumber;
	
	private String fileNumber;
	
	private List<String> careers;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<String> getCareers() {
		return careers;
	}

	public void setCareers(List<String> careers) {
		this.careers = careers;
	}
	
}
