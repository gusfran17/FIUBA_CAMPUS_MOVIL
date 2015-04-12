package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Orientation;

@Repository
public interface OrientationRepository extends CrudRepository<Orientation, Integer> {
	
	@Query(value = "SELECT * FROM orientation WHERE code = ?1", nativeQuery = true)
	public List<Orientation> findByCode(Integer code);

}
