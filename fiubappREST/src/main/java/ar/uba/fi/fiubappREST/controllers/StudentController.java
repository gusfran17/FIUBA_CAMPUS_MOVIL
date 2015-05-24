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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentState;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentStateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;
import ar.uba.fi.fiubappREST.services.SessionService;
import ar.uba.fi.fiubappREST.services.StudentService;

@Controller
@RequestMapping("students")
public class StudentController {	
	
	private StudentService studentService;
	private SessionService sessionService;
	
	@Autowired
	public StudentController(StudentService studentService, SessionService sessionService) {
		super();
		this.studentService = studentService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Student addStudent(@RequestBody StudentCreationRepresentation studentRepresentation) {
		return studentService.create(studentRepresentation);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<StudentProfileRepresentation> findStudents(@RequestHeader(value="Authorization") String token, @RequestParam(value="name", required=false) String name, @RequestParam(value="lastName", required=false) String lastName, @RequestParam(value="email", required=false) String email, @RequestParam(value="careerCode", required=false) String careerCode, @RequestParam(value="fileNumber", required=false) String fileNumber, @RequestParam(value="passportNumber", required=false) String passportNumber, @RequestParam(value="state", required=false) String state) {
		Session session = this.sessionService.findSession(token);
		if(session.isAdminSession()){
			StudentState studentState = (state==null) ? null : StudentState.create(state);
			return studentService.findByProperties(name, lastName, fileNumber, passportNumber, studentState);
		}
		return studentService.findByProperties(session.getUserName(), name, lastName, email, careerCode, fileNumber, passportNumber);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="{userName}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Student getStudent(@RequestHeader(value="Authorization") String token, @PathVariable String userName){
		this.sessionService.validateThisStudentOrMate(token, userName);
		return this.studentService.findOne(userName);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="{userName}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Student updateStudent(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody StudentUpdateRepresentation studentRepresentation){
		this.sessionService.validateThisStudent(token, userName);
		return this.studentService.update(userName, studentRepresentation);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="{userName}/state")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody StudentStateRepresentation updateStudentState(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody StudentStateRepresentation stateRepresentation){
		this.sessionService.validateAdminSession(token);
		return this.studentService.updateStudentState(userName, stateRepresentation); 
	}
}


