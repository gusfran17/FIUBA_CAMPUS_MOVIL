package ar.uba.fi.fiubappREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("sessions/students")
public class StudentSessionController {
	
	private StudentSessionService studentSessionService;
	
	@Autowired
	public StudentSessionController(StudentSessionService studentSessionService) {
		super();
		this.studentSessionService = studentSessionService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody StudentSession createSession(@RequestBody Credentials credentials) {
		return studentSessionService.create(credentials);
	}
}
