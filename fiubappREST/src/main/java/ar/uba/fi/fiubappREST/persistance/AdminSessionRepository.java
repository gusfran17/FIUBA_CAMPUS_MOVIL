package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.AdminSession;

@Repository
public interface AdminSessionRepository extends CrudRepository<AdminSession, String> {
	
	public AdminSession findByToken(String token);

}
