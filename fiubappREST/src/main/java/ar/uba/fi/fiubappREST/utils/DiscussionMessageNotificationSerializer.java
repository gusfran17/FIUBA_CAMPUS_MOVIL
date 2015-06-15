package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ar.uba.fi.fiubappREST.domain.DiscussionMessageNotification;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageNotificationRepresentation;

public class DiscussionMessageNotificationSerializer extends JsonSerializer<DiscussionMessageNotification> {  
	
	@Override
	public void serialize(DiscussionMessageNotification notification, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		DiscussionMessageNotificationRepresentation result = new DiscussionMessageNotificationRepresentation();
		result.setId(notification.getId());
		result.setCreationDate(notification.getCreationDate());
		result.setIsViewed(notification.getIsViewed());
		result.setType(notification.getType());
		result.setGroupId(notification.getGroup().getId());
		result.setDiscussionId(notification.getDiscussion().getId());
		result.setDiscussionName(notification.getDiscussion().getDiscussionName());
		result.setDiscussionMessageId(notification.getDiscussionMessage().getId());
		result.setDiscussionMessageCreationDate(notification.getDiscussionMessage().getCreationDate());
		result.setCommenter(notification.getDiscussionMessage().getCreator());
		
		jsonGenerator.writeObject(result);
	}

}