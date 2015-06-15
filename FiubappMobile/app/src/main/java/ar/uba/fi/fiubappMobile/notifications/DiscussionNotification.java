package ar.uba.fi.fiubappMobile.notifications;
import com.fiubapp.fiubapp.Alumno;

public class DiscussionNotification extends Notification {

    public Alumno getCommenter() {
        return commenter;
    }

    public void setCommenter(Alumno commenter) {
        this.commenter = commenter;
    }

    private Alumno commenter;
    private String groupId;
    private String discussionId;
    private String discussionName;
    private String discussionMessageId;
    private String discussionMessageCreationDate;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public String getDiscussionName() {
        return discussionName;
    }

    public void setDiscussionName(String discussionName) {
        this.discussionName = discussionName;
    }

    public String getDiscussionMessageId() {
        return discussionMessageId;
    }

    public void setDiscussionMessageId(String discussionMessageId) {
        this.discussionMessageId = discussionMessageId;
    }

    public String getDiscussionMessageCreationDate() {
        return discussionMessageCreationDate;
    }

    public void setDiscussionMessageCreationDate(String discussionMessageCreationDate) {
        this.discussionMessageCreationDate = discussionMessageCreationDate;
    }
}
