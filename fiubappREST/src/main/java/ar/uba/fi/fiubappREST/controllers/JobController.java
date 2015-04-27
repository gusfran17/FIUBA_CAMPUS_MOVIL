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

import ar.uba.fi.fiubappREST.domain.Job;
import ar.uba.fi.fiubappREST.services.JobService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("students/{userName}/jobs")
public class JobController {	
	
	private JobService jobService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public JobController(JobService jobService, StudentSessionService studentSessionService) {
		super();
		this.jobService = jobService;
		this.studentSessionService = studentSessionService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Job addJob(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody Job job) {
		this.studentSessionService.validateMine(token, userName);
		return this.jobService.create(userName, job);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Set<Job> getJobs(@RequestHeader(value="Authorization") String token, @PathVariable String userName) {
		this.studentSessionService.validateMineOrMate(token, userName);
		return this.jobService.findAll(userName);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Job updateJob(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer id, @RequestBody Job job) {
		this.studentSessionService.validateMine(token, userName);
		return this.jobService.update(userName, id, job);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleteJob(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer id) {
		this.studentSessionService.validateMine(token, userName);
		this.jobService.delete(userName, id);
	}
}


