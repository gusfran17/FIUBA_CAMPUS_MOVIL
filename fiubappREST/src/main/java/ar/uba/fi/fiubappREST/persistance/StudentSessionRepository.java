package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.StudentSession;

@Repository
public interface StudentSessionRepository extends CrudRepository<StudentSession, String> {
	
	public StudentSession findByToken(String token);

}
