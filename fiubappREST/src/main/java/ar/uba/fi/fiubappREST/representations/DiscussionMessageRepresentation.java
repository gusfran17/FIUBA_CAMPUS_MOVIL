package ar.uba.fi.fiubappREST.representations;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializerDetailed;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializerDetailed;

public class DiscussionMessageRepresentation {
	private String message;
	private Date creationDate;
	private Boolean hasAttachedFile;
	private String attachedFile;
	private StudentProfileRepresentation creator; 

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@JsonSerialize(using = CustomDateSerializerDetailed.class)
	public Date getCreationDate() {
		return creationDate;
	}
	@JsonDeserialize(using = CustomDateDeserializerDetailed.class)
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public StudentProfileRepresentation getCreator() {
		return creator;
	}
	public void setCreator(
			StudentProfileRepresentation studentRepresentation) {
		this.creator = studentRepresentation;
	}
	
	public Boolean getHasAttachedFile() {
		return hasAttachedFile;
	}
	
	public void setHasAttachedFile(Boolean hasAttachedFile) {
		this.hasAttachedFile = hasAttachedFile;
	}
	
	public String getAttachedFile() {
		return attachedFile;
	}
	
	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}
}
