package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Job;

@Repository
public interface JobRepository extends CrudRepository<Job, Integer> {
	
	public void delete(Job job);
	
	@Query(value = "SELECT * FROM job WHERE userName = ?1 AND id = ?2 ORDER BY dateTo IS NULL DESC, dateTo DESC", nativeQuery = true)
	public Job findByUserNameAndId(String userName, Integer id);
}
