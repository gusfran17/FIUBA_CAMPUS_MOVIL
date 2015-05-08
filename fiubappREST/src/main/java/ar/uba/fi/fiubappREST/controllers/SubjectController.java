package ar.uba.fi.fiubappREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Credits;
import ar.uba.fi.fiubappREST.services.SubjectService;

@Controller
@RequestMapping("students/{userName}/careers/{careerCode}/subjects")
public class SubjectController {
	private SubjectService subjectService;
	
	@Autowired
	public SubjectController(SubjectService subjectService){
		this.subjectService = subjectService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Credits getSubjects(@PathVariable String userName, @PathVariable Integer careerCode) {
		return this.subjectService.findAll(careerCode, userName);
	}


}
