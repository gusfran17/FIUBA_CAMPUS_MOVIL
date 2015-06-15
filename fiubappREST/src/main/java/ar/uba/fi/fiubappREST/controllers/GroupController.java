package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.GroupPicture;
import ar.uba.fi.fiubappREST.domain.GroupState;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupStateRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupUpdateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("groups")
public class GroupController {	
	
	private GroupService groupService;
	private SessionService sessionService;
	
	
	@Autowired
	public GroupController(GroupService groupService, SessionService sessionService) {
		super();
		this.groupService = groupService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody GroupRepresentation createGroup(@RequestHeader(value="Authorization") String token, @RequestBody GroupCreationRepresentation groupRepresentation) {
		this.sessionService.validateThisStudent(token, groupRepresentation.getOwnerUserName());
		return groupService.create(groupRepresentation);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<GroupRepresentation> findGroups(@RequestHeader(value="Authorization") String token, @RequestParam(value="name", required=false) String name, @RequestParam(value="state", required=false) String state) {
		Session session = this.sessionService.findSession(token);
		if(session.isAdminSession()){
			GroupState groupState = (state==null) ? null : GroupState.create(state);
			return this.groupService.findByPropertiesForAdmin(name, groupState);
		}
		return this.groupService.findByPropertiesForStudent(session.getUserName(), name);
	}
	
	@RequestMapping(value="{groupId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody GroupRepresentation get(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId) {
		Session session = this.sessionService.findStudentSession(token);
		return groupService.findGroupForStudent(groupId, session.getUserName());
	}
	
	@RequestMapping(value="{groupId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody GroupRepresentation update(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestBody GroupUpdateRepresentation groupRepresentation) {
		Session session = this.sessionService.findStudentSession(token);
		return groupService.updateGroup(groupId, groupRepresentation, session.getUserName());
	}
	
	@RequestMapping(value="{groupId}/picture", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getGroupPicture(@PathVariable Integer groupId) {
		GroupPicture picture = this.groupService.getPicture(groupId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(picture.getContentType()));
		return new ResponseEntity<byte[]>(picture.getImage(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="{groupId}/picture", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody void updateGroupPicture(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestParam("image") MultipartFile image) {
		Session session = this.sessionService.findStudentSession(token);
		this.groupService.updatePicture(groupId, image, session.getUserName());
	}

	@RequestMapping(value="{groupId}/{members}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<StudentProfileRepresentation> getMembers(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId) {
		Session session = this.sessionService.findStudentSession(token);
		return groupService.getMembers(groupId, session.getUserName());
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="{groupId}/state")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody GroupStateRepresentation updateGroupState(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestBody GroupStateRepresentation stateRepresentation) {
		this.sessionService.validateAdminSession(token);
		return this.groupService.updateGroupState(groupId, stateRepresentation); 
	}
}


