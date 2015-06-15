package ar.uba.fi.fiubappREST.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyMemberOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.StudentIsNotMemberOfGroupException;
import ar.uba.fi.fiubappREST.utils.CustomDateDeserializer;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;
import ar.uba.fi.fiubappREST.utils.SpringContext;

@Entity
@Table(name = "study_group")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Date creationDate;
	
	private GroupState state;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ownerUserName")
	private Student owner;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
	      name="groups_students",
	      joinColumns={@JoinColumn(name="groupId", referencedColumnName="id")},
	      inverseJoinColumns={@JoinColumn(name="userName", referencedColumnName="userName")})
	private Set<Student> members;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, orphanRemoval = true)
	private Set<Discussion> discussions;

	public Set<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(Set<Discussion> discussions) {
		this.discussions = discussions;
	}

	@JsonIgnore
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

	public GroupState getState() {
		return state;
	}

	public void setState(GroupState state) {
		this.state = state;
	}

	public Student getOwner() {
		return owner;
	}

	public void setOwner(Student owner) {
		this.owner = owner;
	}

	@JsonIgnore
	public Set<Student> getMembers() {
		return members;
	}

	public void setMembers(Set<Student> members) {
		this.members = members;
	}
	
	@JsonProperty(value = "groupPicture")
	public String getGroupPictureUrl() {
		String baseUrl = (String) SpringContext.getApplicationContext().getBean("baseUrl");
		return baseUrl + "/api/groups/" + this.id + "/picture";
	}

	public void addMember(Student member) {
		if(this.isAlreadyMember(member)){
			throw new StudentAlreadyMemberOfGroupException(member.getUserName(), this.name);
		}
		this.members.add(member);
		member.getGroups().add(this);
	}
	
	public void removeMember(Student member) {
		if(!this.isAlreadyMember(member)){
			throw new StudentIsNotMemberOfGroupException(member.getUserName(), this.name);
		}
		this.members.remove(member);
		member.getGroups().remove(this);
	}
	
	private boolean isAlreadyMember(final Student member) {
		Student foundMember = (Student) CollectionUtils.find(this.members, new Predicate() {
            public boolean evaluate(Object object) {
                return ((Student) object).getUserName().equals(member.getUserName());
            }
		});
		return foundMember!=null;
	}

	public void addDiscussion(Discussion discussion) {
		this.discussions.add(discussion);
		
	}
	
}
