package ar.uba.fi.fiubappREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;
import ar.uba.fi.fiubappREST.services.ProfilePictureService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("students/{userName}/picture")
public class ProfilePictureController {	
	
	private ProfilePictureService profilePictureService;
	private StudentSessionService studentSessionService;
	
	@Autowired
	public ProfilePictureController(ProfilePictureService profilePictureService, StudentSessionService studentSessionService) {
		super();
		this.profilePictureService = profilePictureService;
		this.studentSessionService = studentSessionService;
	}
		
			
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody void updateProfilePicture(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestParam("image") MultipartFile image) {
		this.studentSessionService.validateMine(token, userName);
		this.profilePictureService.update(userName, image);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable String userName) {
		ProfilePicture picture = this.profilePictureService.findByUserName(userName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(picture.getContentType()));
		return new ResponseEntity<byte[]>(picture.getImage(), headers, HttpStatus.OK);
	}
	
}


