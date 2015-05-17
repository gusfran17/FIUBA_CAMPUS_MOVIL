package ar.uba.fi.fiubappREST.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;

@Controller
@RequestMapping("prueba")
public class PruebaController {	
		
	@RequestMapping( method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public String test(@RequestParam("message") DiscussionMessage discussion, @RequestPart("file") MultipartFile file) {
	    String contentType = file.getContentType();
	    System.out.println(contentType);
	    DiscussionMessage d = new DiscussionMessage();
	    d.setMessage("hola que tal");
	    d.setCreatorUserName("89007");
	    return d.toString();
	}
//	
//	
//		
//			
//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseStatus(value = HttpStatus.CREATED)
//	public @ResponseBody void updateProfilePicture(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestParam("image") MultipartFile image) {
//		this.studentSessionService.validateMine(token, userName);
//		this.profilePictureService.update(userName, image);
//	}
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<byte[]> getProfilePicture(@PathVariable String userName) {
//		ProfilePicture picture = this.profilePictureService.findByUserName(userName);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.valueOf(picture.getContentType()));
//		return new ResponseEntity<byte[]>(picture.getImage(), headers, HttpStatus.OK);
//	}
//	
}


