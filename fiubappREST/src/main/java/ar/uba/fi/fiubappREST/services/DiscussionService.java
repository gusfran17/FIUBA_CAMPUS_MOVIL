package ar.uba.fi.fiubappREST.services;


import ar.uba.fi.fiubappREST.domain.Message;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.MessageCreationRepresentation;



public interface DiscussionService {

	DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID);

	Message createMessage(MessageCreationRepresentation messageRepresentation, Integer groupId, Integer discussionId);

}
