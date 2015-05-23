package ar.uba.fi.fiubappREST.persistance;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Discussion;

@Repository
public interface DiscussionRepository extends CrudRepository<Discussion, Integer>{

	
}
