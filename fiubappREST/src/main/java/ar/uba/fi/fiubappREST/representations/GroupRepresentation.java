package ar.uba.fi.fiubappREST.representations;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;

public class GroupRepresentation {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Date creationDate;
	
	private StudentProfileRepresentation owner;
	
	private Integer amountOfMembers;
	
	private Boolean amIAMember;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreationDate() {
		return creationDate;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public StudentProfileRepresentation getOwner() {
		return owner;
	}

	public void setOwner(StudentProfileRepresentation owner) {
		this.owner = owner;
	}

	public Integer getAmountOfMembers() {
		return amountOfMembers;
	}

	public void setAmountOfMembers(Integer amountOfMembers) {
		this.amountOfMembers = amountOfMembers;
	}

	public Boolean getAmIAMember() {
		return amIAMember;
	}

	public void setAmIAMember(Boolean amIAMember) {
		this.amIAMember = amIAMember;
	}
	
}
