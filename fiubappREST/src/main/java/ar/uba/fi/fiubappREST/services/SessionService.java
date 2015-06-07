package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Session;

public interface SessionService {
	
	public Session findSession(String token);
	
	
	public Session createStudentSession(Credentials credentials);
	
	public Session findStudentSession(String token);
	
	public void validateStudentSession(String token);

	public void validateThisStudent(String token, String userName);
	
	public void validateThisStudentOrMate(String token, String userName);

	public void deleteStudentSession(String token);
	
	public Session createAdminSession(Credentials credentials);
	
	public Session findAdminSession(String token);
	
	public void validateAdminSession(String token);

	public void deleteAdminSession(String token);



}
