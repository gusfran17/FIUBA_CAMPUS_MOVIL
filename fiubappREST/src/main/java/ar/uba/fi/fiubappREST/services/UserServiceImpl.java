package ar.uba.fi.fiubappREST.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.User;
import ar.uba.fi.fiubappREST.exceptions.UserAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.UserNotFoundException;
import ar.uba.fi.fiubappREST.persistance.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	@Override
	public User findOne(Integer id) {
		LOGGER.info(String.format("Finding user with id %s.", id));
		User user = userRepository.findOne(id);
		if(user == null){
			LOGGER.error(String.format("User with id %s was not found.", id));
			throw new UserNotFoundException(id);
		}
		LOGGER.info(String.format("User with id %s was found.", id));
		return user;
	}

	@Override
	public User save(User user) {
		LOGGER.info(String.format("Creating user with userName %s.", user.getUserName()));
		try{
			userRepository.save(user);
		} catch (DataIntegrityViolationException e){
			LOGGER.error(String.format("User with userName %s already exists.", user.getUserName()));
			throw new UserAlreadyExistsException(user.getUserName());
		}
		LOGGER.info(String.format("User with userName %s was saved with id %s.", user.getName(), user.getId()));
		return user;
	}

}
