package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Job;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.InvalidDateRangeException;
import ar.uba.fi.fiubappREST.exceptions.InvalidJobInformationForStudentException;
import ar.uba.fi.fiubappREST.persistance.JobRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class JobServiceImplTest {
	
	private static final int A_JOB_ID = 2;
	
	private static final int ANOTHER_JOB_ID = 3;

	private static final String A_POSITION = "aPosition";

	private static final String A_COMPANY_NAME = "aCompanyName";
	
	private static final String ANOTHER_POSITION = "anotherPosition";

	private static final String ANOTHER_COMPANY_NAME = "anotherCompanyName";

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private JobRepository jobRepository;
	@Mock
	private StudentRepository studentRepository;
	
	private Job job;
	
	private Job anotherJob;
	
	private SimpleDateFormat sdf;
	
	private Student student;
		
	private JobService service;
	
	@Before
	public void setUp() throws ParseException{
		this.jobRepository = mock(JobRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.service= new JobServiceImpl(jobRepository, studentRepository);
		
		this.job = new Job();
		this.job.setId(A_JOB_ID);
		this.job.setCompany(A_COMPANY_NAME);
		this.job.setPosition(A_POSITION);
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.job.setDateFrom(sdf.parse("01/01/2001"));
		this.job.setDateTo(sdf.parse("01/01/2001"));
		
		anotherJob = new Job();
		anotherJob.setId(ANOTHER_JOB_ID);
		this.job.setCompany(ANOTHER_COMPANY_NAME);
		this.job.setPosition(ANOTHER_POSITION);
		this.job.setDateFrom(sdf.parse("02/02/2001"));
		this.job.setDateTo(sdf.parse("02/02/2002"));
		
		this.student = new Student(); 
		this.student.setJobs(new HashSet<Job>());
	}
		
	@Test
	public void testCreate() {
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.jobRepository.save(job)).thenReturn(job);
		
		Job savedJob = this.service.create(AN_USER_NAME, job);
		
		assertEquals(savedJob, job);
	}
	

	@Test(expected=InvalidDateRangeException.class)
	public void testCreateFutureDateFrom() throws ParseException {
		this.job.setDateFrom(sdf.parse("01/01/2030"));
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		
		this.service.create(AN_USER_NAME, job);
	}
	
	@Test(expected=InvalidDateRangeException.class)
	public void testCreateFutureDateTo() throws ParseException {
		this.job.setDateTo(sdf.parse("01/01/2030"));
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		
		this.service.create(AN_USER_NAME, job);
	}
	
	@Test(expected=InvalidDateRangeException.class)
	public void testCreateInvalidDateRange() throws ParseException {
		this.job.setDateFrom(new Date());
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		
		this.service.create(AN_USER_NAME, job);
	}
	
	@Test
	public void testFindAll() {
		Set<Job> jobs = new HashSet<Job>();
		jobs.add(job);
		jobs.add(anotherJob);
		this.student.setJobs(jobs);
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		
		Set<Job> foundJobs = this.service.findAll(AN_USER_NAME);
		
		assertEquals(2, foundJobs.size());
	}

	@Test
	public void testUpdate() throws ParseException {
		when(this.jobRepository.findByUserNameAndId(AN_USER_NAME, A_JOB_ID)).thenReturn(job);
		when(this.jobRepository.save(anotherJob)).thenReturn(anotherJob);
		
		Job updatedJob = this.service.update(AN_USER_NAME, A_JOB_ID, anotherJob);
		
		assertEquals(updatedJob.getCompany(), anotherJob.getCompany());
		assertEquals(updatedJob.getPosition(), anotherJob.getPosition());
		assertEquals(updatedJob.getDateFrom(), anotherJob.getDateFrom());
		assertEquals(updatedJob.getDateTo(), anotherJob.getDateTo());
	}
	
	@Test(expected=InvalidJobInformationForStudentException.class)
	public void testUpdateNotFound() throws ParseException {
		when(this.jobRepository.findByUserNameAndId(AN_USER_NAME, A_JOB_ID)).thenReturn(null);
		
		this.service.update(AN_USER_NAME, A_JOB_ID, anotherJob);
	}
	
	@Test
	public void testDelete() {
		when(this.studentRepository.findByUserNameAndFetchJobsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.jobRepository.findByUserNameAndId(AN_USER_NAME, A_JOB_ID)).thenReturn(job);
		
		this.service.delete(AN_USER_NAME, A_JOB_ID);
		
		assertTrue(true);
	}
	
}
