package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.StudentSession;

public interface StudentSessionService {
	
	public StudentSession create(Credentials credentials);
	
	public StudentSession find(String token);
	
	public void validate(String token);

	public void validateMine(String token, String userName);
	
	public void validateMineOrMate(String token, String userName);

}
