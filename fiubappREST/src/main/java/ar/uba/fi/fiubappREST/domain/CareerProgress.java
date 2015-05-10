package ar.uba.fi.fiubappREST.domain;

import java.util.List;

public class CareerProgress {

	private Integer currentAmount;
	private Integer totalAmount;
	List<Subject> subjects;
	
	public CareerProgress (List<Subject> subjects, Integer totalAmount){
		this.totalAmount = totalAmount;
		this.subjects = subjects;
		this.currentAmount = sumUpCredits();
		
	}

	private Integer sumUpCredits() {
		Integer currentAmount = 0;
		for (Subject subject : this.subjects) {
			currentAmount += subject.getCredits();
		}
		return currentAmount;
	}

	public Integer getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Integer currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
