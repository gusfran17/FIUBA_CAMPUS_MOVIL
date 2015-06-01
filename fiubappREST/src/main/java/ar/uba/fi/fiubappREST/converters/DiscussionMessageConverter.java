package ar.uba.fi.fiubappREST.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;

@Component
public class DiscussionMessageConverter {
	private StudentProfileConverter studentConverter;
	
	@Autowired
	public DiscussionMessageConverter(StudentProfileConverter studentConverter){
		this.studentConverter = studentConverter;
	}

	public DiscussionMessageRepresentation convert(DiscussionMessage message){
		DiscussionMessageRepresentation discussionMessageRepresentation = new DiscussionMessageRepresentation();
		discussionMessageRepresentation.setCreator(this.studentConverter.convert(message.getCreator()));
		discussionMessageRepresentation.setCreationDate(message.getCreationDate());	
		discussionMessageRepresentation.setMessage(message.getMessage());
		return discussionMessageRepresentation;
	}


}
