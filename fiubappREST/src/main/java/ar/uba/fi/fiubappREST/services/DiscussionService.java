package ar.uba.fi.fiubappREST.services;



import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;



public interface DiscussionService {

	DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID);

	DiscussionMessageRepresentation createMessage(Integer groupId, Integer discussionId, String message, String userName, MultipartFile file);

	Set<DiscussionRepresentation> findGroupDiscussionsForMember(Integer groupId, String userName);

	GroupRepresentation findGroupForStudent(Integer groupId, String userName);

	Set<DiscussionMessage> findGroupDiscussionMessagesForMember(Integer groupId, Integer discussionId, String userName);

}
