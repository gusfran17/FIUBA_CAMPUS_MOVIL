package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;

public interface GroupService {

	public GroupRepresentation create(GroupCreationRepresentation groupRepresentation);

	public List<GroupRepresentation> findByProperties(String userName, String name);

	public GroupRepresentation registerStudent(String userName, Integer groupId);

	public List<GroupRepresentation> getStudentGroups(String userName);

	public GroupRepresentation findGroupForStudent(Integer groupId, String userName);

}
