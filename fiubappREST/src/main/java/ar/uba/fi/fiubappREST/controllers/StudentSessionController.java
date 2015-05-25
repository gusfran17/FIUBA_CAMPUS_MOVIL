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

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("sessions/students")
public class StudentSessionController {
	
	private SessionService sessionService;
	
	@Autowired
	public StudentSessionController(SessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Session createSession(@RequestBody Credentials credentials) {
		return sessionService.createStudentSession(credentials);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Session findSession(@RequestHeader(value="Authorization") String token) {
		return sessionService.findStudentSession(token);
	}
}
