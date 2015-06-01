package ar.uba.fi.fiubappREST.persistance;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;

@Repository
public interface DiscussionMessageRepository extends CrudRepository<DiscussionMessage, Integer> {

	@Query(value = "SELECT * FROM message WHERE id in  (SELECT messages_id FROM discussion_message WHERE discussion_id = ?1) ORDER BY creationDate", nativeQuery = true)
	public Set<DiscussionMessage> findMessagesByProperties(Integer discussionId);

}
