package ar.uba.fi.fiubappREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Application;
import ar.uba.fi.fiubappREST.services.NotificationService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("students/{userName}/applications")
public class ApplicationController {	
	
	private NotificationService notificationService;
	private SessionService sessionService;
	
	@Autowired
	public ApplicationController(NotificationService notificationService, SessionService sessionService) {
		super();
		this.notificationService = notificationService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Application createApplication(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody Application application) {
		this.sessionService.validateThisStudent(token, application.getApplicantUserName());
		return this.notificationService.createApplicationNotification(userName, application);
	}
}


