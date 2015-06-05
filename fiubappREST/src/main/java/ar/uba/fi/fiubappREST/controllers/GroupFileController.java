package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

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

import ar.uba.fi.fiubappREST.domain.GroupFile;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.services.GroupFileService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("groups/{groupId}/files")
public class GroupFileController {	
	
	private GroupFileService groupFileService;
	private SessionService sessionService;
	
	@Autowired
	public GroupFileController(GroupFileService groupFileService, SessionService sessionService) {
		super();
		this.groupFileService = groupFileService;
		this.sessionService = sessionService;
	}
					
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody GroupFile loadGroupFile(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestParam("file") MultipartFile file) {
		Session session = this.sessionService.findStudentSession(token);
		return this.groupFileService.loadFile(groupId, session.getUserName(), file);
	}
	
	@RequestMapping(value="{fileId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getGroupFile(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @PathVariable Integer fileId) {
		Session session = this.sessionService.findStudentSession(token);		
		GroupFile file = this.groupFileService.findGroupFile(groupId, fileId, session.getUserName());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(file.getContentType()));
		return new ResponseEntity<byte[]>(file.getFile(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<GroupFile> getGroupFiles(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId) {
		Session session = this.sessionService.findStudentSession(token);
		return this.groupFileService.getFiles(groupId, session.getUserName());
	}
	
}


