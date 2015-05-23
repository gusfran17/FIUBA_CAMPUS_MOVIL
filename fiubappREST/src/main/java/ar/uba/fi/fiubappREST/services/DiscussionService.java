package ar.uba.fi.fiubappREST.services;


import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;



public interface DiscussionService {

	DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID);

}
