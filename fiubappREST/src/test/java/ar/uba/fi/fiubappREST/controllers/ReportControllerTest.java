package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.DiscussionReportInformation;
import ar.uba.fi.fiubappREST.services.ReportService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class ReportControllerTest {
	
	private final static Integer VALUES = 12;
	private static final String AN_ADMIN_TOKEN = "anAdminToken";
	
	private ReportController controller;
	
	@Mock
	private ReportService service;
	@Mock
	private SessionService sessionService;
		
	@Before
	public void setUp(){
		this.service = mock(ReportService.class);
		this.sessionService = mock(SessionService.class);
		this.controller = new ReportController(sessionService, service);
	}
	
	@Test 
	public void testGetMostActiveDiscussions(){
		doNothing().when(this.sessionService).validateAdminSession(AN_ADMIN_TOKEN);
		DiscussionReportInformation aRegister = mock(DiscussionReportInformation.class);
		DiscussionReportInformation anotherRegister = mock(DiscussionReportInformation.class);
		DiscussionReportInformation yetAnotherRegister = mock(DiscussionReportInformation.class);
		List<DiscussionReportInformation> information = new ArrayList<DiscussionReportInformation>();
		information.add(aRegister);
		information.add(anotherRegister);
		information.add(yetAnotherRegister);
		Date dateFrom = mock(Date.class);
		Date dateTo = mock(Date.class);
		when(this.service.getMostActiveDiscussions(dateFrom, dateTo, VALUES)).thenReturn(information);
				
		List<DiscussionReportInformation> foundInformation = this.controller.getMostActiveDiscussions(AN_ADMIN_TOKEN, dateFrom, dateTo, VALUES);
		
		assertEquals(information, foundInformation);
	} 

}

