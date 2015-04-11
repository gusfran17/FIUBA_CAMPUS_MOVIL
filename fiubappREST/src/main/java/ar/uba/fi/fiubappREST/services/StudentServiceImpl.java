package ar.uba.fi.fiubappREST.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.StudentConverter;
import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	private StudentRepository studentRepository;
	private CareerRepository careerRepository;
	private StudentConverter studentConverter;
	private Md5PasswordEncoder passwordEncoder;
	private StudentProfileConverter studentProfileConverter;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, CareerRepository careerRepository, StudentConverter studentConverter, 
			Md5PasswordEncoder passwordEncoder, StudentProfileConverter studentProfileConverter){
		this.studentRepository = studentRepository;
		this.careerRepository = careerRepository;
		this.studentConverter = studentConverter;
		this.passwordEncoder = passwordEncoder;
		this.studentProfileConverter = studentProfileConverter;
	}

	@Override
	public Student create(StudentCreationRepresentation studentRepresentation) {
		Student student = this.getStudent(studentRepresentation);
		LOGGER.info(String.format("Creating student with userName %s and careerCode.", student.getUserName(), studentRepresentation.getCareerCode()));
		this.verifyUnusedUserName(student.getUserName());
		Career career = this.getCareer(studentRepresentation.getCareerCode());
		this.createStudentCareer(student, career);
		student = studentRepository.save(student); 
		LOGGER.info(String.format("Student with userName %s and careerCode %s was created.", student.getUserName(), studentRepresentation.getCareerCode()));
		return student;
	}
	
	private Student getStudent(StudentCreationRepresentation studentRepresentation){
		String encodedPassword = this.passwordEncoder.encodePassword(studentRepresentation.getPassword(), null);
		studentRepresentation.setPassword(encodedPassword);		
		return this.studentConverter.convert(studentRepresentation);
	}
	
	private void verifyUnusedUserName(String userName) {
		LOGGER.info(String.format("Verifying wether student with userName %s already exists.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student != null){
			LOGGER.error(String.format("Student with userName %s already exists.", userName));
			throw new StudentAlreadyExistsException(userName); 
		}		
		LOGGER.info(String.format("Student with userName %s doesn't exist.", userName));
	}

	private Career getCareer(Integer careerCode) {
		LOGGER.info(String.format("Finding career with code %s.", careerCode));
		Career career = this.careerRepository.findByCode(careerCode);
		if(career == null){
			LOGGER.error(String.format("Career with code %s was not found.", careerCode));
			throw new CareerNotFoundException(careerCode);
		}
		LOGGER.info(String.format("Career with code %s was found.", careerCode));
		return career;
	}

	private StudentCareer createStudentCareer(Student student, Career career) {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);
		return studentCareer;
	}

	@Override
	public List<StudentProfileRepresentation> findAll() {
		LOGGER.info(String.format("Finding all students."));
		List<Student> students = this.studentRepository.findAll();
		List<StudentProfileRepresentation> profiles = new ArrayList<StudentProfileRepresentation>();
		for (Student student : students) {
			profiles.add(this.studentProfileConverter.convert(student));
		}
		LOGGER.info(String.format("All students were found."));
		return profiles;
	}

	@Override
	public Student findOne(String userName) {
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}

	
}
