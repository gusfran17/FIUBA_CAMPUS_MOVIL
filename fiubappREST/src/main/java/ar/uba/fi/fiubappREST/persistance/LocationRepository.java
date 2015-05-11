package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{
	
	@Query(value = "SELECT * FROM location WHERE userName = ?1", nativeQuery = true)
	public Location findByUserName(String userName);
	
}
