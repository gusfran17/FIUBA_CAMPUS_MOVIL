package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;

public interface StudentService {
		
	public Student create(StudentCreationRepresentation studentRepresentation);
	
	public List<StudentProfileRepresentation> findAllFor(String string);
	
	public Student findOne(String userName);

	public Student update(String userName, StudentUpdateRepresentation studentRepresentation);

}
