package ar.uba.fi.fiubappREST.controllers;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.DiscussionMessage;

@Controller
@RequestMapping("prueba")
public class PruebaController {	
		
	@RequestMapping(method = RequestMethod.POST, consumes = {"multipart/*"})
	public ResponseEntity<byte[]> test(@RequestPart("file") MultipartFile file, @RequestPart("message") DiscussionMessage discussion) throws IOException {
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(file.getContentType()));
		return new ResponseEntity<byte[]>(file.getBytes(), headers, HttpStatus.OK);
	}	
}


