package ar.uba.fi.fiubappREST.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.Message;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.MessageCreationRepresentation;


@Component
public class DiscussionConverter {

	public DiscussionRepresentation convert(Discussion discussion){
		DiscussionRepresentation discussionRepresentation = new DiscussionRepresentation();
		discussionRepresentation.setCreatorUserName(discussion.getCreator().getUserName());
		discussionRepresentation.setName(discussion.getDiscussionName());	
		List<Message> messages = discussion.getMessages();
		List<MessageCreationRepresentation> messagesRepresentation = new ArrayList<MessageCreationRepresentation>();
		for (int i= 0; i<messages.size();i++){
			Message message = messages.get(i);
			
			MessageCreationRepresentation messageRepresentation = new MessageCreationRepresentation();
			messageRepresentation.setCreationDate(message.getCreationDate());
			messageRepresentation.setCreatorUserName(message.getCreator().getUserName());
			messageRepresentation.setMessage(message.getMessage());
			messagesRepresentation.add(messageRepresentation);
			
		}
		discussionRepresentation.setMessages(messagesRepresentation);
		return discussionRepresentation;
	}

}
