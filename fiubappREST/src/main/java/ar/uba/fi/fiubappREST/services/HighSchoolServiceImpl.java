package ar.uba.fi.fiubappREST.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.HighSchool;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.HighSchoolAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.exceptions.HighSchoolNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.InvalidDateRangeException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.HighSchoolRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class HighSchoolServiceImpl implements HighSchoolService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HighSchoolServiceImpl.class);
	
	private HighSchoolRepository highSchoolRepository;
	private StudentRepository studentRepository;
		
	@Autowired
	public HighSchoolServiceImpl(HighSchoolRepository highSchoolRepository, StudentRepository studentRepository){
		this.highSchoolRepository = highSchoolRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public HighSchool create(String userName, HighSchool highSchool) {
		LOGGER.info(String.format("Creating high school information for student with userName %s.", userName));
		this.validateHighSchoolDoesntExist(userName);
		this.validateDatesRange(highSchool.getDateFrom(), highSchool.getDateTo());
		Student student = this.studentRepository.findOne(userName);
		highSchool.setStudent(student);
		student.setHighSchool(highSchool);
		student = studentRepository.save(student);
		LOGGER.info(String.format("High school information for student with userName %s was created.", userName));
		return student.getHighSchool();
	}

	private void validateDatesRange(Date dateFrom, Date dateTo) {
		Date today = new Date();
		if(dateFrom.after(today) || dateTo.after(today) || dateFrom.after(dateTo)){
			LOGGER.error(String.format("High school dates information is not valid."));
			throw new InvalidDateRangeException();
		}		
	}

	private void validateHighSchoolDoesntExist(String userName) {
		HighSchool foundHighSchool = this.highSchoolRepository.findByUserName(userName);
		if(foundHighSchool!=null){
			LOGGER.error(String.format("High school information already exists for student with userName %s.", userName));
			throw new HighSchoolAlreadyExistsForStudentException(userName);
		}
	}

	@Override
	public HighSchool findByUserName(String userName) {
		LOGGER.info(String.format("Finding high school information student with userName %s.", userName));
		this.verifyStudentExists(userName);
		HighSchool highSchool = this.highSchoolRepository.findByUserName(userName);
		if(highSchool==null){
			LOGGER.error(String.format("High school information not found for student with userName %s.", userName));
			throw new HighSchoolNotFoundForStudentException(userName);
		}
		LOGGER.info(String.format("High school information found for student with userName %s.", userName));
		return highSchool;
	}

	private void verifyStudentExists(String userName) {
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Studen with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName);
		}
	}

	@Override
	public void delete(String userName) {
		LOGGER.info(String.format("Deleting high school information for student with userName %s.", userName));
		this.findByUserName(userName);
		Student student = this.studentRepository.findOne(userName);
		student.setHighSchool(null);
		studentRepository.save(student);
		LOGGER.info(String.format("High school information for student with userName %s was deleted.", userName));
	}

	@Override
	public HighSchool update(String userName, HighSchool highSchool) {
		LOGGER.info(String.format("Updatng high school information for student with userName %s.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student.getHighSchool()==null){
			LOGGER.error(String.format("High school information not found for student with userName %s.", userName));
			throw new HighSchoolNotFoundForStudentException(userName);
		}
		this.validateDatesRange(highSchool.getDateFrom(), highSchool.getDateTo());
		student.getHighSchool().setDegree(highSchool.getDegree());
		student.getHighSchool().setSchoolName(highSchool.getSchoolName());
		student.getHighSchool().setDateFrom(highSchool.getDateFrom());
		student.getHighSchool().setDateTo(highSchool.getDateTo());
		student = studentRepository.save(student);
		LOGGER.info(String.format("High school information for student with userName %s was updated.", userName));
		return student.getHighSchool();
	}

}