package ar.uba.fi.fiubappREST.representations;

public class StudentCreationRepresentation {

	private String password;
	
	private String name;
	
	private String lastName;
	
	private String email;
	
	private Boolean isExchangeStudent;
	
	private Integer careerCode;

	private String fileNumber;
	
	private String passportNumber;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsExchangeStudent() {
		return isExchangeStudent;
	}

	public void setIsExchangeStudent(Boolean isExchangeStudent) {
		this.isExchangeStudent = isExchangeStudent;
	}

	public Integer getCareerCode() {
		return careerCode;
	}

	public void setCareerCode(Integer careerCode) {
		this.careerCode = careerCode;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
}
