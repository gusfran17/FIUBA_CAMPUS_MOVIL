package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageCreationRepresentation;



public interface DiscussionService {

	DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID);

	DiscussionMessageRepresentation createMessage(DiscussionMessageCreationRepresentation messageRepresentation, Integer groupId, Integer discussionId);

}
