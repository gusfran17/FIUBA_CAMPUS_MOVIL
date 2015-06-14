package ar.uba.fi.fiubappREST.domain;

public class StudentCareerInformation {
	
	private Integer careerCode;
	
	private String careerName;
	
	private Long amountOfStudents;

	public Integer getCareerCode() {
		return careerCode;
	}

	public void setCareerCode(Integer careerCode) {
		this.careerCode = careerCode;
	}

	public String getCareerName() {
		return careerName;
	}

	public void setCareerName(String careerName) {
		this.careerName = careerName;
	}

	public Long getAmountOfStudents() {
		return amountOfStudents;
	}

	public void setAmountOfStudents(Long amountOfStudents) {
		this.amountOfStudents = amountOfStudents;
	}
}
