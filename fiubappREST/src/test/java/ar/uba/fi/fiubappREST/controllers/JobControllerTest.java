package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Job;
import ar.uba.fi.fiubappREST.services.JobService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class JobControllerTest {
	
	private static final int JOB_ID = 3;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private JobController controller;
	
	@Mock
	private JobService service;
	@Mock
	private StudentSessionService studentSessionService;
	@Mock
	private Job aJob;
	@Mock
	private Job anotherJob;
	
	@Before
	public void setUp(){
		this.service = mock(JobService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new JobController(service, studentSessionService);
		
		this.aJob = mock(Job.class);
		this.anotherJob = mock(Job.class);
	}

	@Test
	public void testAddJob() {
		when(service.create(AN_USER_NAME, aJob)).thenReturn(aJob);
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
				
		Job createdJob = this.controller.addJob(A_TOKEN, AN_USER_NAME, aJob);
		
		assertEquals(createdJob, aJob);		
	}
	
	@Test
	public void testGetJobs() {
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		Set<Job> jobs = new HashSet<Job>();
		jobs.add(aJob);
		jobs.add(anotherJob);
		when(service.findAll(AN_USER_NAME)).thenReturn(jobs);
				
		Set<Job> foundJobs = this.controller.getJobs(A_TOKEN, AN_USER_NAME);
		
		assertEquals(2, foundJobs.size());		
	}
	
	@Test
	public void testUpdateJob() {
		when(service.update(AN_USER_NAME, JOB_ID, aJob)).thenReturn(aJob);
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
				
		Job updatedJob = this.controller.updateJob(A_TOKEN, AN_USER_NAME, JOB_ID, aJob);
		
		assertEquals(updatedJob, aJob);		
	}
	
	@Test
	public void testDeleteJob() {
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		doNothing().when(service).delete(AN_USER_NAME, JOB_ID);
				
		this.controller.deleteJob(A_TOKEN, AN_USER_NAME, JOB_ID);
		
		assertTrue(true);		
	}
	
}

