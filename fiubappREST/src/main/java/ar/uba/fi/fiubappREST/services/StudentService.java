package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;

public interface StudentService {
		
	public Student create(StudentCreationRepresentation studentRepresentation);
	
	public List<StudentProfileRepresentation> findAllFor(String userName);
	
	public Student findOne(String userName);

	public Student update(String userName, StudentUpdateRepresentation studentRepresentation);

	public List<StudentProfileRepresentation> findByProperties(String myUserName, String name, String lastName, String email, String careerCode, String fileNumber, String passport);

}
