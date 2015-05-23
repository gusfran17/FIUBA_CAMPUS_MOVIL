package ar.uba.fi.fiubappREST.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity(name = "session")
public class Session {
	
	@Id
	private String userName;
	
	private String token;
	
	private Date creationDate;
	
	private SessionRole role;

	@JsonIgnore
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonIgnore
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonIgnore
	public SessionRole getRole() {
		return role;
	}

	public void setRole(SessionRole role) {
		this.role = role;
	}
	
	public boolean isAdminSession(){
		return this.role == SessionRole.ADMIN;
	}
	
}
