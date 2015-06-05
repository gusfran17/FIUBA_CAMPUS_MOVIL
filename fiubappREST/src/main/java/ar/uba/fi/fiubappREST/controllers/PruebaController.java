package ar.uba.fi.fiubappREST.controllers;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.services.ProfilePictureService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("prueba")
public class PruebaController {	
		
	private ProfilePictureService profilePictureService;
	
	@Autowired
	public PruebaController(ProfilePictureService profilePictureService) {
		super();
		this.profilePictureService = profilePictureService;		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void test(@RequestParam("file") MultipartFile file, @RequestParam(value="text") String text, @RequestParam(value="userName") String userName) throws IOException {	    
		DiscussionMessage message = new ObjectMapper().readValue(text, DiscussionMessage.class);
		this.profilePictureService.update(userName, file);
	}
}
