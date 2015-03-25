package ar.uba.fi.mobileCampusREST.services;

import ar.uba.fi.mobileCampusREST.domain.User;

public interface UserService {
	
	public User findOne(Integer id);
		
	public User save(User user);

}
