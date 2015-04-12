package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.HighSchool;

@Repository
public interface HighSchoolRepository extends CrudRepository<HighSchool, Integer> {
	
	@Query(value = "SELECT * FROM high_school WHERE userName = ?1", nativeQuery = true)
	public HighSchool findByUserName(String userName);
	
	public void delete(HighSchool highSchool);
}
