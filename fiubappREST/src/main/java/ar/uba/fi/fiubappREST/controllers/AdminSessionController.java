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

import ar.uba.fi.fiubappREST.domain.AdminSession;
import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.services.AdminSessionService;

@Controller
@RequestMapping("sessions/administrators")
public class AdminSessionController {
	
	private AdminSessionService adminSessionService;
	
	@Autowired
	public AdminSessionController(AdminSessionService adminSessionService) {
		super();
		this.adminSessionService = adminSessionService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody AdminSession createSession(@RequestBody Credentials credentials) {
		return this.adminSessionService.create(credentials);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody AdminSession findSession(@RequestHeader(value="Authorization") String token) {
		return this.adminSessionService.find(token);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleSession(@RequestHeader(value="Authorization") String token) {
		this.adminSessionService.delete(token);
	}
}
