package ar.uba.fi.fiubappREST.controllers;

import java.util.Set;

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

import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.WallMessage;
import ar.uba.fi.fiubappREST.representations.WallMessageCreationRepresentation;
import ar.uba.fi.fiubappREST.services.SessionService;
import ar.uba.fi.fiubappREST.services.WallMessageService;

@Controller
@RequestMapping("students/{userName}/wall/messages")
public class WallMessageController {	
	
	private WallMessageService wallMessageService;
	private SessionService sessionService;
	
	
	@Autowired
	public WallMessageController(WallMessageService wallMessageService, SessionService sessionService) {
		super();
		this.wallMessageService = wallMessageService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody WallMessage createWallMessage(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody WallMessageCreationRepresentation message) {
		this.sessionService.validateThisStudentOrMate(token, userName);
		return wallMessageService.create(userName, message);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Set<WallMessage> getWallMessages(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.sessionService.validateThisStudentOrMate(token, userName);
		Session session = this.sessionService.findStudentSession(token);
		return wallMessageService.find(userName, session.getUserName());
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleteMessage(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer id) {
		this.sessionService.validateThisStudent(token, userName);
		this.wallMessageService.delete(userName, id);
	}
	

}


