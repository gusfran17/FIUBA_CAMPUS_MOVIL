package ar.uba.fi.fiubappREST.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ar.uba.fi.fiubappREST.utils.DiscussionMessageNotificationSerializer;

@Entity
@DiscriminatorValue("DISCUSSION_MESSAGE_NOTIFICATION")
@JsonSerialize(using = DiscussionMessageNotificationSerializer.class)
public class DiscussionMessageNotification extends Notification {
		
	@OneToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	@OneToOne
	@JoinColumn(name="discussionId")
	private Discussion discussion;
	
	@OneToOne
	@JoinColumn(name="messageId")
	private DiscussionMessage discussionMessage;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	public DiscussionMessage getDiscussionMessage() {
		return discussionMessage;
	}

	public void setDiscussionMessage(DiscussionMessage discussionMessage) {
		this.discussionMessage = discussionMessage;
	}

}
