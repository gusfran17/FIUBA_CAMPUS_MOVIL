package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.AdminSession;
import ar.uba.fi.fiubappREST.domain.Credentials;

public interface AdminSessionService {
	
	public AdminSession create(Credentials credentials);
	
	public AdminSession find(String token);
	
	public void validate(String token);

	public void delete(String token);

}
