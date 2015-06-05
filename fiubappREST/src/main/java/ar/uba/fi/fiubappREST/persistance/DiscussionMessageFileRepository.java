package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.DiscussionMessageFile;

@Repository
public interface DiscussionMessageFileRepository extends CrudRepository<DiscussionMessageFile, Integer>{

}
