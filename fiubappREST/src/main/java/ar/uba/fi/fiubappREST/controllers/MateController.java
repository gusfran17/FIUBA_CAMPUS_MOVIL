package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

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

import ar.uba.fi.fiubappREST.representations.MateCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.services.MateService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("students/{userName}/mates")
public class MateController {	
	
	private MateService mateService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public MateController(MateService mateService, StudentSessionService studentSessionService) {
		super();
		this.mateService = mateService;
		this.studentSessionService = studentSessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody StudentProfileRepresentation becomeMates(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody MateCreationRepresentation mate) {
		this.studentSessionService.validateMine(token, userName);
		return this.mateService.becomeMates(userName, mate.getUserName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<StudentProfileRepresentation> getMates(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.studentSessionService.validateMineOrMate(token, userName);
		return this.mateService.getMates(userName);
	}
}


