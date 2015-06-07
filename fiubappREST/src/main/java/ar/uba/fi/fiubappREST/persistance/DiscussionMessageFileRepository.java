package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.DiscussionMessageFile;

@Repository
public interface DiscussionMessageFileRepository extends CrudRepository<DiscussionMessageFile, Integer>{

	@Query(value = "SELECT * FROM discussion_message_file WHERE messageId = ?1", nativeQuery = true)
	public DiscussionMessageFile findByMessageId(Integer messageId);

}
