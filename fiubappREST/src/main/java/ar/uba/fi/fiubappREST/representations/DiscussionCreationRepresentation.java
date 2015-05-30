package ar.uba.fi.fiubappREST.representations;

public class DiscussionCreationRepresentation {
	
	private String creatorUserName;
	private String discussionName;
	private String firstMessage;

	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String userName) {
		this.creatorUserName = userName;
	}
	public String getDiscussionName() {
		return discussionName;
	}
	public void setDiscussionName(String discussionName) {
		this.discussionName = discussionName;
	}
	public String getFirstMessage() {
		return firstMessage;
	}
	public void setFirstMessage(String firstMessage) {
		this.firstMessage = firstMessage;
	}

}
