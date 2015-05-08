package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ar.uba.fi.fiubappREST.domain.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer>{

	@Query(value = "SELECT * FROM subject WHERE careerCode = ?1 AND userName = ?2", nativeQuery = true)
	public List<Subject> findByUserNameAndCareerCode(Integer careerCode, String userName);
	
}
