package ar.uba.fi.fiubappREST.services;

import java.util.Set;

import ar.uba.fi.fiubappREST.domain.WallMessage;
import ar.uba.fi.fiubappREST.representations.WallMessageCreationRepresentation;

public interface WallMessageService {

	public WallMessage create(String userName, WallMessageCreationRepresentation message);

	public Set<WallMessage> find(String ownerUserName, String petitionerUserName);

	public void delete(String userName, Integer id);

}
