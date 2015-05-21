package ar.uba.fi.fiubappREST.representations;

public class DiscussionCreationRepresentation {
	
	private String creatorUserName;
	private String name;
	private String firstMessage;

	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String userName) {
		this.creatorUserName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstMessage() {
		return firstMessage;
	}
	public void setFirstMessage(String firstMessage) {
		this.firstMessage = firstMessage;
	}

}
