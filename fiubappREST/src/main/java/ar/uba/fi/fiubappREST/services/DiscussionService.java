package ar.uba.fi.fiubappREST.services;


import java.util.Set;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;



public interface DiscussionService {

	DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID);

	DiscussionMessage createMessage(DiscussionMessageCreationRepresentation messageRepresentation, Integer groupId, Integer discussionId);

	Set<DiscussionRepresentation> findGroupDiscussionsForMember(Integer groupId, String userName);

	GroupRepresentation findGroupForStudent(Integer groupId, String userName);

}
