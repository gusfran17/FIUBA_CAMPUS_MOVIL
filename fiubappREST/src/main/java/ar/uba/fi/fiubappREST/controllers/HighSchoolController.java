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

import ar.uba.fi.fiubappREST.domain.HighSchool;
import ar.uba.fi.fiubappREST.services.HighSchoolService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("students/{userName}/highSchool")
public class HighSchoolController {	
	
	private HighSchoolService highSchoolService;
	private SessionService sessionService;
	
	@Autowired
	public HighSchoolController(HighSchoolService highSchoolService, SessionService sessionService) {
		super();
		this.highSchoolService = highSchoolService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody HighSchool addHigSchoolInformation(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody HighSchool highSchool) {
		this.sessionService.validateThisStudent(token, userName);
		return this.highSchoolService.create(userName, highSchool);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody HighSchool getHigSchoolInformation(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.sessionService.validateThisStudentOrMate(token, userName);
		return this.highSchoolService.findByUserName(userName);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleteHigSchoolInformation(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.sessionService.validateThisStudent(token, userName);
		this.highSchoolService.delete(userName);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody HighSchool updateHigSchoolInformation(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody HighSchool highSchool) {
		this.sessionService.validateThisStudent(token, userName);
		return this.highSchoolService.update(userName, highSchool);
	}
}


