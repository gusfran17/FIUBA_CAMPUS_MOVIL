package ar.uba.fi.fiubappREST.services;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.comparators.NullComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Job;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.InvalidDateRangeException;
import ar.uba.fi.fiubappREST.exceptions.InvalidJobInformationForStudentException;
import ar.uba.fi.fiubappREST.persistance.JobRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class JobServiceImpl implements JobService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);
	
	private JobRepository jobRepository;
	private StudentRepository studentRepository;
		
	@Autowired
	public JobServiceImpl(JobRepository jobRepository, StudentRepository studentRepository){
		this.jobRepository = jobRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public Job create(String userName, Job job) {
		LOGGER.info(String.format("Adding job information for student with userName %s.", userName));
		this.validateDatesRange(job.getDateFrom(), job.getDateTo());
		Student student = this.studentRepository.findByUserNameAndFetchJobsEagerly(userName);
		job.setStudent(student);
		student.addJob(job);
		job = this.jobRepository.save(job);
		LOGGER.info(String.format("Job information for student with userName %s was added.", userName));
		return job;
	}

	private void validateDatesRange(Date dateFrom, Date dateTo) {
		if(dateFrom!=null && dateTo!=null){
			Date today = new Date();
			if(dateFrom.after(today) || dateTo.after(today) || dateFrom.after(dateTo)){
				LOGGER.error(String.format("Job dates information is not valid."));
				throw new InvalidDateRangeException();
			}		
		}
	}

	@Override
	public Set<Job> findAll(String userName) {
		LOGGER.info(String.format("Finding jobs information for student with userName %s.", userName));
		Student student = this.studentRepository.findByUserNameAndFetchJobsEagerly(userName);
		LOGGER.info(String.format("Jobs information for student with userName %s was found.", userName));
		return this.orderJobs(student.getJobs());
	}

	private Set<Job> orderJobs(Set<Job> jobs) {
		TreeSet<Job> sortedSet = new TreeSet<Job>(new Comparator<Job>() {			
			public int compare(Job aJob, Job anotherJob) {
				NullComparator comparator = new NullComparator(true);
				return comparator.compare(aJob.getDateTo(), anotherJob.getDateTo());
			}
		});
		sortedSet.addAll(jobs);
		return sortedSet.descendingSet();
	}

	@Override
	public Job update(String userName, Integer id, Job job) {
		LOGGER.info(String.format("Updating job information for job with id %s for student with userName %s.", job.getId(), userName));
		this.validateDatesRange(job.getDateFrom(), job.getDateTo());
		Job savedJob = findJob(userName, id);
		updateJob(job, savedJob);
		this.jobRepository.save(savedJob);
		LOGGER.info(String.format("Job information for job with id %s for student with userName %s was updated.", job.getId(), userName));
		return savedJob;
	}

	private void updateJob(Job newJob, Job oldJob) {
		oldJob.setCompany(newJob.getCompany());
		oldJob.setPosition(newJob.getPosition());
		oldJob.setDateFrom(newJob.getDateFrom());
		oldJob.setDateTo(newJob.getDateTo());
	}

	private Job findJob(String userName, Integer id) {
		Job savedJob = this.jobRepository.findByUserNameAndId(userName, id);
		if(savedJob==null){
			LOGGER.error(String.format("Job information for job with id %s for student with userName %s was not found.", id, userName));
			throw new InvalidJobInformationForStudentException(id, userName);
		}
		return savedJob;
	}

	@Override
	public void delete(String userName, Integer id) {
		LOGGER.info(String.format("Deleting job information for job with id %s for student with userName %s.", id, userName));
		Student student = this.studentRepository.findByUserNameAndFetchJobsEagerly(userName);
		Job job = findJob(userName, id);
		student.deleteJob(job);
		this.studentRepository.save(student);	
		LOGGER.info(String.format("Job information for job with id %s for student with userName %s was deleted.", job.getId(), userName));
	}
	
}
