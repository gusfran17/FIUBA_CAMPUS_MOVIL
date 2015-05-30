package ar.uba.fi.fiubappREST.persistance;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.WallMessage;

public interface WallMessageRepository extends CrudRepository<WallMessage, Integer>{

	@Query(value = "SELECT * FROM wall_message WHERE userName = ?1 ORDER BY creationDate DESC", nativeQuery = true)
	public Set<WallMessage> findByUserName(String ownerUserName);

	@Query(value = "SELECT * FROM wall_message WHERE id = ?1 AND userName = ?2", nativeQuery = true)
	public List<WallMessage> findByIdAndUserName(Integer id, String userName);
	
}
