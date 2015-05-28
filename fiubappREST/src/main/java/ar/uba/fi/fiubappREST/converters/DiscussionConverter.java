package ar.uba.fi.fiubappREST.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.Message;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.MessageCreationRepresentation;


@Component
public class DiscussionConverter {

	public DiscussionRepresentation convert(Discussion discussion){
		DiscussionRepresentation discussionRepresentation = new DiscussionRepresentation();
		discussionRepresentation.setId(discussion.getId());
		discussionRepresentation.setCreatorUserName(discussion.getCreator().getName() + " " + discussion.getCreator().getLastName());
		discussionRepresentation.setDiscussionName(discussion.getDiscussionName());	
		discussionRepresentation.setCreationDate(discussion.getCreationDate());

		Set<Message> messages = discussion.getMessages();
		List<MessageCreationRepresentation> messagesRepresentation = new ArrayList<MessageCreationRepresentation>();

		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()){
			Message message = iterator.next();
			
			MessageCreationRepresentation messageRepresentation = new MessageCreationRepresentation();
			messageRepresentation.setCreationDate(message.getCreationDate());
			messageRepresentation.setCreatorUserName(discussion.getCreator().getName() + " " + discussion.getCreator().getLastName());
			messageRepresentation.setMessage(message.getMessage());
			messagesRepresentation.add(messageRepresentation);
			
		}
		discussionRepresentation.setMessages(messagesRepresentation);
		return discussionRepresentation;
	}

}