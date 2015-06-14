package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Configuration;
import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.WallConfiguration;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Integer>{
				
	@Query(value = "SELECT * FROM configuration WHERE userName = ?1 AND type = 'LOCATION_CONFIGURATION'", nativeQuery = true)
	public LocationConfiguration findLocationConfigurationByUserName(String userName);
	
	@Query(value = "SELECT * FROM configuration WHERE userName = ?1 AND type = 'WALL_CONFIGURATION'", nativeQuery = true)
	public WallConfiguration findWallConfigurationByUserName(String userName);
	
}
