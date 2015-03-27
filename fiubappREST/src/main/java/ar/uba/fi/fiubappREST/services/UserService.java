package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.User;

public interface UserService {
	
	public User findOne(Integer id);
		
	public User save(User user);

}
