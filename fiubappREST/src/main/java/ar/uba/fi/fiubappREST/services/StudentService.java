package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;

public interface StudentService {
		
	public Student create(StudentCreationRepresentation studentRepresentation);

}
