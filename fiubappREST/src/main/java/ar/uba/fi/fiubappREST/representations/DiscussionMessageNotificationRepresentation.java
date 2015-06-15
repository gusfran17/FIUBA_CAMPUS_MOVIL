package ar.uba.fi.fiubappREST.representations;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.utils.CustomDateSerializer;
import ar.uba.fi.fiubappREST.utils.StudentProfileSerializer;


public class DiscussionMessageNotificationRepresentation {

	private Integer id;
	
	private Date creationDate;
	
	private Boolean isViewed;
	
	private String type;
	
	private Integer groupId;
	
	private Integer discussionId;
	
	private String discussionName;
	
	private Integer discussionMessageId;
	
	private Date discussionMessageCreationDate;
	
	private Student commenter;

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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Boolean isViewed) {
		this.isViewed = isViewed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(Integer discussionId) {
		this.discussionId = discussionId;
	}

	public String getDiscussionName() {
		return discussionName;
	}

	public void setDiscussionName(String discussionName) {
		this.discussionName = discussionName;
	}

	public Integer getDiscussionMessageId() {
		return discussionMessageId;
	}

	public void setDiscussionMessageId(Integer discussionMessageId) {
		this.discussionMessageId = discussionMessageId;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDiscussionMessageCreationDate() {
		return discussionMessageCreationDate;
	}

	public void setDiscussionMessageCreationDate(Date discussionMessageCreationDate) {
		this.discussionMessageCreationDate = discussionMessageCreationDate;
	}

	@JsonSerialize(using = StudentProfileSerializer.class)
	public Student getCommenter() {
		return commenter;
	}

	public void setCommenter(Student commenter) {
		this.commenter = commenter;
	}
}
