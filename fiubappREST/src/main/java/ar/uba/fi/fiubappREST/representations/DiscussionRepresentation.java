package ar.uba.fi.fiubappREST.representations;

import java.util.List;

public class DiscussionRepresentation {
	private String creatorUserName;
	private String name;
	private List<MessageCreationRepresentation> messages;

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
	public List<MessageCreationRepresentation> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageCreationRepresentation> message) {
		this.messages = message;
	}
	
}
