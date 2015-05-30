package ar.uba.fi.fiubappREST.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import ar.uba.fi.fiubappREST.utils.CustomDateSerializerDetailed;
import ar.uba.fi.fiubappREST.utils.CustomDateDeserializerDetailed;

@Entity
@Table(name = "message")
public class DiscussionMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String message;
	
	private Date creationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="creatorUserName")
	private Student creator;

	@JsonIgnore
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
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
	public Student getCreator() {
		return creator;
	}
	public void setCreator(Student creator) {
		this.creator = creator;
	}
	
}
