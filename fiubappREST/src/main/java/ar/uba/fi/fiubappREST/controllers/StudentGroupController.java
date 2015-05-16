package ar.uba.fi.fiubappREST.controllers;

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
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("students/{userName}/groups")
public class StudentGroupController {	
	
	private GroupService groupService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public StudentGroupController(GroupService groupService, StudentSessionService studentSessionService) {
		super();
		this.groupService = groupService;
		this.studentSessionService = studentSessionService;
	}
		
	@RequestMapping(value="{groupId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody GroupRepresentation signOnGroup(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer groupId) {
		this.studentSessionService.validateMine(token, userName);
		return this.groupService.registerStudent(userName, groupId);
	}
}


