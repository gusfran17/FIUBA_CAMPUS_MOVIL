package ar.uba.fi.fiubappREST.domain;

public class DiscussionReportInformation {
	
	private String groupName;
	
	private String discussionName;
	
	private Integer amountOfComments;
	
	private Integer amountOfGroupMembers;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDiscussionName() {
		return discussionName;
	}

	public void setDiscussionName(String discussionName) {
		this.discussionName = discussionName;
	}

	public Integer getAmountOfComments() {
		return amountOfComments;
	}

	public void setAmountOfComments(Integer amountOfComments) {
		this.amountOfComments = amountOfComments;
	}

	public Integer getAmountOfGroupMembers() {
		return amountOfGroupMembers;
	}

	public void setAmountOfGroupMembers(Integer amountOfGroupMembers) {
		this.amountOfGroupMembers = amountOfGroupMembers;
	}

}
