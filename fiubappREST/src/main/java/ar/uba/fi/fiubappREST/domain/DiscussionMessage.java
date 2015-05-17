package ar.uba.fi.fiubappREST.domain;

import java.io.Serializable;

public class DiscussionMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private String creatorUserName;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

}
