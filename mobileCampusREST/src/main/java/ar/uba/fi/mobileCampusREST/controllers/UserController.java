package ar.uba.fi.mobileCampusREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.mobileCampusREST.domain.User;
import ar.uba.fi.mobileCampusREST.services.UserService;

@Controller
@RequestMapping("users")
public class UserController {	
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody User addUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User getUser(@PathVariable Integer id) {
		return userService.findOne(id);
	}
}


