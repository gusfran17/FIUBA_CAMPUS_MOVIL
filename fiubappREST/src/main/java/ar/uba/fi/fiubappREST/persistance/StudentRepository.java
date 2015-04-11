package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
	
	public List<Student> findAll();

}
