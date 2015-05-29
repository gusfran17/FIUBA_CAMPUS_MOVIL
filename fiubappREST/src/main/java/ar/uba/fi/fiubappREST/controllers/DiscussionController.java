package ar.uba.fi.fiubappREST.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.services.DiscussionService;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

@Controller
@RequestMapping("groups")
public class DiscussionController {
	
	private GroupService groupService;
	private StudentSessionService studentSessionService;
	private DiscussionService discussionService;
	
	@Autowired
	public DiscussionController(GroupService groupService, StudentSessionService studentSessionService, DiscussionService discussionService) {
		super();
		this.groupService = groupService;
		this.studentSessionService = studentSessionService;
		this.discussionService = discussionService;
	}
	
	@RequestMapping(value="{groupId}/discussions", method = RequestMethod.GET)
	public @ResponseBody Set<DiscussionRepresentation> getDiscussionsForGroup(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId) {
		StudentSession studentSession = this.studentSessionService.find(token);
		Set<DiscussionRepresentation> discussions = this.groupService.findGroupDiscussionsForMember(groupId, studentSession.getUserName());	
		return discussions;
	}
	
	@RequestMapping(value="{groupId}/discussions", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody DiscussionRepresentation createGroupDiscussion(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @RequestBody DiscussionCreationRepresentation discussionRepresentation) {
		this.studentSessionService.validateMine(token, discussionRepresentation.getCreatorUserName());
		return this.discussionService.create(discussionRepresentation, groupId);
	}

	@RequestMapping(value="{groupId}/discussions/{discussionId}/messages", method = RequestMethod.GET)
	public @ResponseBody Set<DiscussionMessageRepresentation> getMessagesForGroupDiscussion(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @PathVariable Integer discussionId) {
		StudentSession studentSession = this.studentSessionService.find(token);
		Set<DiscussionMessageRepresentation> messages = this.groupService.findGroupDiscussionMessagesForMember(groupId, discussionId, studentSession.getUserName());	
		return messages;
	}
	
	@RequestMapping(value="{groupId}/discussions/{discussionId}/messages", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody DiscussionMessageRepresentation createGroupDiscussionMessage(@RequestHeader(value="Authorization") String token, @PathVariable Integer groupId, @PathVariable Integer discussionId, @RequestBody DiscussionMessageCreationRepresentation messageRepresentation) {
		this.studentSessionService.validateMine(token, messageRepresentation.getCreatorUserName());
		return this.discussionService.createMessage(messageRepresentation, groupId, discussionId);
	}


}
