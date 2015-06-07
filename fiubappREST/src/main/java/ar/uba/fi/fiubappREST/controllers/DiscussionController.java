package ar.uba.fi.fiubappREST.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.DiscussionMessageFile;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.services.DiscussionService;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("groups")
public class DiscussionController {
	
	private SessionService sessionService;
	private DiscussionService discussionService;
	private GroupService groupService;
	
	@Autowired
	public DiscussionController(SessionService sessionService, DiscussionService discussionService, GroupService groupService) {
		super();
		this.sessionService = sessionService;
		this.discussionService = discussionService;
		this.groupService = groupService;
	}
	
	@RequestMapping(value="{groupId}/discussions", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Set<DiscussionRepresentation> getDiscussionsForGroup(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId) {
		Session studentSession = this.sessionService.findStudentSession(token);
		Set<DiscussionRepresentation> discussions = this.groupService.findGroupDiscussionsForMember(groupId, studentSession.getUserName());	
		return discussions;
	}
	
	@RequestMapping(value="{groupId}/discussions", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody DiscussionRepresentation createGroupDiscussion(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestBody DiscussionCreationRepresentation discussionRepresentation) {
		this.sessionService.validateThisStudent(token, discussionRepresentation.getCreatorUserName());
		return this.discussionService.create(discussionRepresentation, groupId);
	}

	@RequestMapping(value="{groupId}/discussions/{discussionId}/messages", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody DiscussionMessageRepresentation createGroupDiscussionMessage(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @PathVariable Integer discussionId, @RequestParam(required=false) MultipartFile file, @RequestParam("message") String message) {
		Session session = this.sessionService.findStudentSession(token);
		return this.discussionService.createMessage(groupId, discussionId, message, session.getUserName(), file);
	}
	
	@RequestMapping(value="{groupId}/discussions/{discussionId}/messages", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<DiscussionMessageRepresentation> getMessagesForGroupDiscussion(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @PathVariable Integer discussionId) {
		Session session = this.sessionService.findStudentSession(token);
		List<DiscussionMessageRepresentation> messages = this.discussionService.findGroupDiscussionMessagesForMember(groupId, discussionId, session.getUserName());	
		return messages;
	}
	
	@RequestMapping(value="{groupId}/discussions/{discussionId}/messages/{messageId}/file", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable Integer groupId, @PathVariable Integer discussionId, @PathVariable Integer messageId) {
		DiscussionMessageFile file = this.discussionService.findDiscussionMessageFile(groupId, discussionId, messageId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(file.getContentType()));
		return new ResponseEntity<byte[]>(file.getFile(), headers, HttpStatus.OK);
	}

}