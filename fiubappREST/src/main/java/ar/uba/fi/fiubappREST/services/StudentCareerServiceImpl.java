package ar.uba.fi.fiubappREST.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentCareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class StudentCareerServiceImpl implements StudentCareerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCareerServiceImpl.class);
	
	private CareerRepository careerRepository;
	private StudentRepository studentRepository;
	private StudentCareerRepository studentCareerRepository;
		
	@Autowired
	public StudentCareerServiceImpl(CareerRepository careerRepository, StudentRepository studentRepository, StudentCareerRepository studentCareerRepository){
		this.careerRepository = careerRepository;
		this.studentRepository = studentRepository;
		this.studentCareerRepository = studentCareerRepository;
	}

	@Override
	public StudentCareer create(String userName, Integer careerCode) {
		LOGGER.info(String.format("Adding career with code %s to student with userName %s.", careerCode, userName));
		Career career = findCareer(careerCode);
		Student student = findStudent(userName);
		StudentCareer studentCareer = this.createStudentCareer(student, career);
		student = studentRepository.save(student); 
		LOGGER.info(String.format("Career with code %s was added to student with userName %s.", careerCode, userName));
		return studentCareer;
	}
	
	private StudentCareer createStudentCareer(Student student, Career career) {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);
		return studentCareer;
	}

	private Student findStudent(String userName) {
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		return student;
	}

	private Career findCareer(Integer careerCode) {
		Career career = this.careerRepository.findByCode(careerCode);
		if(career == null){
			LOGGER.error(String.format("Career with code %s was not found.", careerCode));
			throw new CareerNotFoundException(careerCode);
		}
		return career;
	}

	@Override
	public List<StudentCareer> findAll(String userName) {
		LOGGER.info(String.format("Finding all careers for student with userName %s.", userName));
		Student student = this.findStudent(userName);
		LOGGER.info(String.format("All careers for student with userName %s were found.", userName));
		return student.getCareers();
	}

	@Override
	public void delete(String userName, Integer code) {
		LOGGER.info(String.format("Deleting career with code %s for student with userName %s.", code, userName));
		StudentCareer studentCareer = this.findStudentCareer(userName, code);
		Student student = this.findStudent(userName);
		student.removeCareer(studentCareer);
		this.studentRepository.save(student);
		LOGGER.info(String.format("Career with code %s for student with userName %s was deletes.", code, userName));
	}

	private StudentCareer findStudentCareer(String userName, Integer code) {
		StudentCareer studentCareer = studentCareerRepository.findByCodeAndUserName(code, userName);
		if(studentCareer == null){
			LOGGER.error(String.format("Career with code %s for student with userName %s was not found.", code, userName));
			throw new CareerNotFoundForStudentException(code, userName);
		}
		return studentCareer;
	}

}
