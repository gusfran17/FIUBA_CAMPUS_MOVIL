package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {

	public List<Group> findByName(String name);
	
	@Query(value = 	"SELECT *  "
			+ "FROM study_group g "
			+ "WHERE ( " 
			+ 	"((?1 is null) or (?1='') or (LOWER(g.name) LIKE %?1%)) "
			+   "AND ( (?2 is null)  or (g.state = ?2))"
			+ ")"
			, nativeQuery = true)
	public List<Group> findByProperties(String name, Integer state);
	
	@Query("SELECT g FROM Group g LEFT JOIN FETCH g.discussions AS d WHERE d.id = ?1")
    public Group findGroupForDiscussion(Integer discussionId);

	
}
