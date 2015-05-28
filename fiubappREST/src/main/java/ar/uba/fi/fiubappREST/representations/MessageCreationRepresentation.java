package ar.uba.fi.fiubappREST.representations;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializerDetailed;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializerDetailed;

public class MessageCreationRepresentation {

	private String message;
	private Date creationDate;
	private String creatorUserName;
	
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
	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}


}
