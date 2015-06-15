package ar.uba.fi.fiubappREST.services;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;





import ar.uba.fi.fiubappREST.domain.GroupPicture;
import ar.uba.fi.fiubappREST.domain.GroupState;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupStateRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupUpdateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public interface GroupService {

	public GroupRepresentation create(GroupCreationRepresentation groupRepresentation);

	public List<GroupRepresentation> findByPropertiesForStudent(String userName, String name);

	public GroupRepresentation registerStudent(String userName, Integer groupId);

	public List<GroupRepresentation> getStudentGroups(String userName);

	public GroupRepresentation findGroupForStudent(Integer groupId, String userName);

	public GroupRepresentation updateGroup(Integer groupId, GroupUpdateRepresentation groupRepresentation, String userName);

	public GroupPicture getPicture(Integer groupId);

	public void updatePicture(Integer groupId, MultipartFile image, String userName);

	public Set<DiscussionRepresentation> findGroupDiscussionsForMember(Integer groupId, String string);

	public void unregisterStudent(String userName, Integer groupId);

	public List<StudentProfileRepresentation> getMembers(Integer groupId, String userName);

	public List<GroupRepresentation> findByPropertiesForAdmin(String name, GroupState groupState);

	public GroupStateRepresentation updateGroupState(Integer groupId, GroupStateRepresentation stateRepresentation);

}
