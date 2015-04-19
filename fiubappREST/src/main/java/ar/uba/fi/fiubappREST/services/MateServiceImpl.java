package ar.uba.fi.fiubappREST.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

@Service
public class MateServiceImpl implements MateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MateServiceImpl.class);
	
	private StudentRepository studentRepository;
	private StudentProfileConverter studentProfileConverter;
		
	@Autowired
	public MateServiceImpl(StudentRepository studentRepository, StudentProfileConverter studentProfileConverter){
		this.studentRepository = studentRepository;
		this.studentProfileConverter = studentProfileConverter;
	}

	@Override
	public StudentProfileRepresentation becomeMates(String userName, String mateUserName) {
		Student student = findStudent(userName);
		Student mate = findStudent(mateUserName);
		student.addMate(mate);
		this.studentRepository.save(student);
		this.studentRepository.save(mate);
		StudentProfileRepresentation mateProfile = this.studentProfileConverter.convert(student, mate);
		
		return mateProfile;	
	}
	
	private Student findStudent(String userName) {
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findByUserNameAndFetchMatesEagerly(userName);
		if(student == null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException();
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}

	@Override
	public List<StudentProfileRepresentation> getMates(String userName) {
		Student me = this.findStudent(userName);
		List<StudentProfileRepresentation> mates = new ArrayList<StudentProfileRepresentation>();
		for (Student mate : me.getMates()) {
			mates.add(this.studentProfileConverter.convert(me, mate));
		}
		return mates;
	}

	
}
