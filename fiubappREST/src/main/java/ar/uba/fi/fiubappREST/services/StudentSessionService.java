package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.StudentSession;

public interface StudentSessionService {
	
	public StudentSession create(Credentials credentials);

}
