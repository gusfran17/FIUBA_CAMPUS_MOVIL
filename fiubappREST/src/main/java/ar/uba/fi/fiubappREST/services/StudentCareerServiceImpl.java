package ar.uba.fi.fiubappREST.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class StudentCareerServiceImpl implements StudentCareerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCareerServiceImpl.class);
	
	private CareerRepository careerRepository;
	private StudentRepository studentRepository;
		
	@Autowired
	public StudentCareerServiceImpl(CareerRepository careerRepository, StudentRepository studentRepository){
		this.careerRepository = careerRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public Career create(String userName, Integer careerCode) {
		LOGGER.info(String.format("Adding career with code %s to student with userName %s.", careerCode, userName));
		Career career = findCareer(careerCode);
		Student student = findStudent(userName);
		this.createStudentCareer(student, career);
		student = studentRepository.save(student); 
		LOGGER.info(String.format("Career with code %s was added to student with userName %s.", careerCode, userName));
		return career;
	}
	
	private void createStudentCareer(Student student, Career career) {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);
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

}
