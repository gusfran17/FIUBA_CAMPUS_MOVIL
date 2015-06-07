package ar.uba.fi.fiubappREST.persistance;


import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Discussion;

@Repository
public interface DiscussionRepository extends CrudRepository<Discussion, Integer>{

	@Query(value = "SELECT * FROM discussion WHERE id in  (SELECT discussions_id FROM study_group_discussion WHERE study_group_id = ?1) ORDER BY creationDate DESC", nativeQuery = true)
	public Set<Discussion> findByProperties(Integer groupId);
	
	@Query(value = "SELECT * FROM discussion WHERE id in  (SELECT discussions_id FROM study_group_discussion WHERE study_group_id = ?1) ORDER BY creationDate DESC", nativeQuery = true)
	public Set<Discussion> findForReport();
	
}
