package ar.uba.fi.fiubappREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("groups")
public class GroupController {	
	
	private GroupService groupService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public GroupController(GroupService groupService, StudentSessionService studentSessionService) {
		super();
		this.groupService = groupService;
		this.studentSessionService = studentSessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody GroupRepresentation createGroup(@RequestHeader(value="Authorization") String token, @RequestBody GroupCreationRepresentation groupRepresentation) {
		this.studentSessionService.validateMine(token, groupRepresentation.getOwnerUserName());
		return groupService.create(groupRepresentation);
	}
}


