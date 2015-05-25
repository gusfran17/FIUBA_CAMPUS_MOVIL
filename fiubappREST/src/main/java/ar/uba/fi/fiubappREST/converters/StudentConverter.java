package ar.uba.fi.fiubappREST.converters;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.domain.StudentState;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;

@Component
public class StudentConverter {
	
	private static final String EXCHANGE_STUDENT_PREFIX = "I";

	public Student convert(StudentCreationRepresentation representation){
		Student student = new Student();
		student.setPassword(representation.getPassword());
		student.setName(representation.getName());
		student.setLastName(representation.getLastName());
		student.setEmail(representation.getEmail());
		student.setIsExchangeStudent(representation.getIsExchangeStudent());
		student.setFileNumber(representation.getFileNumber());
		student.setPassportNumber(representation.getPassportNumber());
		student.setCareers(new ArrayList<StudentCareer>());
		String userName = !student.getIsExchangeStudent() ? student.getFileNumber() : EXCHANGE_STUDENT_PREFIX + student.getPassportNumber();
		student.setUserName(userName);
		student.setState(StudentState.PENDING);
		return student;
	}
}
