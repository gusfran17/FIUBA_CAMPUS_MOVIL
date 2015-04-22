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

import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.services.StudentCareerService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("students/{userName}/careers")
public class StudentCareerController {	
	
	private StudentCareerService studentCareerService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public StudentCareerController(StudentCareerService studentCareerService, StudentSessionService studentSessionService) {
		super();
		this.studentCareerService = studentCareerService;
		this.studentSessionService = studentSessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST, value="{code}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody StudentCareer addCareer(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer code) {
		this.studentSessionService.validateMine(token, userName);
		return this.studentCareerService.create(userName, code);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<StudentCareer> getCareers(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.studentSessionService.validateMineOrMate(token, userName);
		return this.studentCareerService.findAll(userName);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="{code}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleteCareer(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer code) {
		this.studentSessionService.validateMine(token, userName);
		this.studentCareerService.delete(userName, code);
	}
}


