package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.SessionRole;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
	
	public Session findByTokenAndRole(String token, SessionRole role);

}
