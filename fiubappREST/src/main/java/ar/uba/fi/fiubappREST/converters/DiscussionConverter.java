package ar.uba.fi.fiubappREST.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageCreationRepresentation;


@Component
public class DiscussionConverter {
	
	private StudentProfileConverter studentConverter;
	
	@Autowired
	public DiscussionConverter(StudentProfileConverter studentConverter){
		this.studentConverter = studentConverter;
	}

	public DiscussionRepresentation convert(Discussion discussion){
		DiscussionRepresentation discussionRepresentation = new DiscussionRepresentation();
		discussionRepresentation.setId(discussion.getId());
		discussionRepresentation.setCreator(this.studentConverter.convert(discussion.getCreator()));
		discussionRepresentation.setDiscussionName(discussion.getDiscussionName());	
		discussionRepresentation.setCreationDate(discussion.getCreationDate());

		Set<DiscussionMessage> messages = discussion.getMessages();
		List<DiscussionMessageCreationRepresentation> messagesRepresentation = new ArrayList<DiscussionMessageCreationRepresentation>();

		Iterator<DiscussionMessage> iterator = messages.iterator();
		while (iterator.hasNext()){
			DiscussionMessage message = iterator.next();
			
			DiscussionMessageCreationRepresentation messageRepresentation = new DiscussionMessageCreationRepresentation();
			messageRepresentation.setCreationDate(message.getCreationDate());
			messageRepresentation.setCreatorUserName(discussion.getCreator().getName() + " " + discussion.getCreator().getLastName());
			messageRepresentation.setMessage(message.getMessage());
			messagesRepresentation.add(messageRepresentation);
			
		}
		discussionRepresentation.setMessages(messagesRepresentation);
		discussionRepresentation.setMessagesAmount(messagesRepresentation.size());
		return discussionRepresentation;
	}

}
