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

import ar.uba.fi.fiubappREST.domain.CareerProgress;
import ar.uba.fi.fiubappREST.services.StudentSessionService;
import ar.uba.fi.fiubappREST.services.SubjectService;

@Controller
@RequestMapping("students/{userName}/careers/{careerCode}/subjects")
public class SubjectController {
	
	private SubjectService subjectService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public SubjectController(SubjectService subjectService, StudentSessionService studentSessionService){
		this.subjectService = subjectService;
		this.studentSessionService = studentSessionService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody CareerProgress getSubjects(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer careerCode) {
		this.studentSessionService.validateMineOrMate(token, userName);
		return this.subjectService.getCareerProgress(careerCode, userName);
	}


}
