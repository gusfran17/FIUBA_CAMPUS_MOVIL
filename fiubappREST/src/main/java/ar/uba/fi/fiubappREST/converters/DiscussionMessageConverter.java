package ar.uba.fi.fiubappREST.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;
import ar.uba.fi.fiubappREST.utils.SpringContext;

@Component
public class DiscussionMessageConverter {
	private StudentProfileConverter studentConverter;
	
	@Autowired
	public DiscussionMessageConverter(StudentProfileConverter studentConverter){
		this.studentConverter = studentConverter;
	}

	public DiscussionMessageRepresentation convert(DiscussionMessage message, Integer groupId, Integer discussionId){
		DiscussionMessageRepresentation discussionMessageRepresentation = new DiscussionMessageRepresentation();
		discussionMessageRepresentation.setCreator(this.studentConverter.convert(message.getCreator()));
		discussionMessageRepresentation.setCreationDate(message.getCreationDate());	
		discussionMessageRepresentation.setMessage(message.getMessage());
		discussionMessageRepresentation.setHasAttachedFile(message.isHasAttachedFile());
		discussionMessageRepresentation.setFileName(message.getFileName());
		discussionMessageRepresentation.setId(message.getId());
		if(message.isHasAttachedFile()){	
			discussionMessageRepresentation.setAttachedFile(this.getAttachedFileUrl(groupId, discussionId, message.getId()));
		}
		return discussionMessageRepresentation;
	}

	private String getAttachedFileUrl(Integer groupId, Integer discussionId, Integer messageId) {
		String baseUrl = (String) SpringContext.getApplicationContext().getBean("baseUrl");
		return baseUrl + "/api/groups/" + groupId + "/discussions/"+ discussionId + "/messages/" + messageId + "/file";
		
	}


}
