package ar.uba.fi.fiubappREST.domain;

public class Credentials {
	
	private String userName;
	
	private String password;
	
	private Boolean isExchangeStudent;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsExchangeStudent() {
		return isExchangeStudent;
	}

	public void setIsExchangeStudent(Boolean isExchangeStudent) {
		this.isExchangeStudent = isExchangeStudent;
	}
	
}
