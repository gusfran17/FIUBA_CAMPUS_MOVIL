package ar.uba.fi.fiubappREST.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.DiscussionReportInformation;
import ar.uba.fi.fiubappREST.domain.MonthlyApprovedStudentsInformation;
import ar.uba.fi.fiubappREST.domain.StudentCareerInformation;
import ar.uba.fi.fiubappREST.services.ReportService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("reports")
public class ReportController {
	
	private ReportService reportService;
	private SessionService sessionService;
	
	@Autowired
	public ReportController(SessionService sessionService, ReportService reportService) {
		super();
		this.sessionService = sessionService;
		this.reportService = reportService;
	}
	
	@RequestMapping(value="groups/discussions", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<DiscussionReportInformation> getMostActiveDiscussions(@RequestHeader(value="Authorization") String token, @RequestParam(value="dateFrom", required=true) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateFrom, @RequestParam(value="dateTo", required=true) @DateTimeFormat(pattern="dd/MM/yyyy") Date dateTo, @RequestParam(value="values", required=true) Integer values) {
		this.sessionService.validateAdminSession(token);
		return this.reportService.getMostActiveDiscussions(dateFrom, dateTo, values);
	}
	
	@RequestMapping(value="students/careers", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<StudentCareerInformation> getStudentsCareers(@RequestHeader(value="Authorization") String token) {
		this.sessionService.validateAdminSession(token);
		return this.reportService.getStudentCareers();
	}
	
	@RequestMapping(value="students/approved", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<MonthlyApprovedStudentsInformation> getApprovedStudents(@RequestHeader(value="Authorization") String token) {
		this.sessionService.validateAdminSession(token);
		return this.reportService.getApprovedStudents();
	}
}
