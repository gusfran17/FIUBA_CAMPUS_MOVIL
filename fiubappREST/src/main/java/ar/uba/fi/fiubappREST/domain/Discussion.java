package ar.uba.fi.fiubappREST.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;


@Entity
@Table(name = "discussion")
public class Discussion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String discussionName;
	
	private Date creationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="creatorUserName")
	private Student creator;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval = true)
	private Set<DiscussionMessage> messages;
	
	public Discussion(){
		super();
	}
	
	public Discussion(String discussionName, Student creator, String discussionMessage){
		super();
		DiscussionMessage message = new DiscussionMessage();
		Set<DiscussionMessage> messages = new HashSet<DiscussionMessage>();
		
		message.setCreationDate(new Date());
		message.setCreator(creator);
		message.setMessage(discussionMessage);
		messages.add(message);
		
		this.messages = messages;
		this.creationDate= new Date();
		this.discussionName = discussionName;
		this.creator = creator;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiscussionName() {
		return discussionName;
	}

	public void setDiscussionName(String name) {
		this.discussionName = name;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreationDate() {
		return creationDate;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Student getCreator() {
		return creator;
	}

	public void setCreator(Student creator) {
		this.creator = creator;
	}

	public Set<DiscussionMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<DiscussionMessage> messages) {
		this.messages = messages;
	}

	public void addMessage(DiscussionMessage message) {
		this.messages.add(message);
	}
	
}
