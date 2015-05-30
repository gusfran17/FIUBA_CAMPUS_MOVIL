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
	
	private Boolean isMyMate;
	
	private String comments;
	
	private String profilePicture;
	
	private String email;
	
	private String state;
	
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

	public Boolean getIsMyMate() {
		return isMyMate;
	}

	public void setIsMyMate(Boolean isMyMate) {
		this.isMyMate = isMyMate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
