package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("students/{userName}/groups")
public class StudentGroupController {	
	
	private GroupService groupService;
	private SessionService sessionService;
	
	@Autowired
	public StudentGroupController(GroupService groupService, SessionService sessionService) {
		super();
		this.groupService = groupService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(value="{groupId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody GroupRepresentation signOnGroup(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer groupId) {
		this.sessionService.validateThisStudent(token, userName);
		return this.groupService.registerStudent(userName, groupId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<GroupRepresentation> getGroups(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.sessionService.validateThisStudent(token, userName);
		return this.groupService.getStudentGroups(userName);
	}
}


