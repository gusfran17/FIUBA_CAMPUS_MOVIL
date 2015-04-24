package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;

public interface GroupService {

	GroupRepresentation create(GroupCreationRepresentation groupRepresentation);

}
