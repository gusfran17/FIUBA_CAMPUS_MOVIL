package ar.uba.fi.fiubappREST.representations;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

public class DiscussionRepresentation {
	private Integer id;
	private Date creationDate;
	private String discussionName;
	private Integer messagesAmount;
	private StudentProfileRepresentation creator;
	private List<DiscussionMessageCreationRepresentation> messages;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreationDate() {
		return creationDate;
	}
	@JsonDeserialize(using = CustomDateDeserializer.class)	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDiscussionName() {
		return discussionName;
	}
	public void setDiscussionName(String name) {
		this.discussionName = name;
	}
	public Integer getMessagesAmount() {
		return messagesAmount;
	}
	public void setMessagesAmount(Integer messagesAmount) {
		this.messagesAmount = messagesAmount;
	}
	public StudentProfileRepresentation getCreator() {
		return creator;
	}
	public void setCreator(StudentProfileRepresentation creator) {
		this.creator = creator;
	}
	public List<DiscussionMessageCreationRepresentation> getMessages() {
		return messages;
	}
	public void setMessages(List<DiscussionMessageCreationRepresentation> message) {
		this.messages = message;
	}
	
}
