package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Notification;
import ar.uba.fi.fiubappREST.services.NotificationService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("students/{userName}/notifications")
public class NotificationController {	
	
	private NotificationService notificationService;
	private SessionService sessionService;
	
	@Autowired
	public NotificationController(NotificationService notificationService, SessionService sessionService) {
		super();
		this.notificationService = notificationService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Notification> getNotifications(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestParam(value="viewed", required=false) Boolean isViewed) {
		this.sessionService.validateThisStudent(token, userName);
		return this.notificationService.find(userName, isViewed);
	}
	
	@RequestMapping(value="{notificationId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Notification markNotificationAsViewed(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer notificationId) {
		this.sessionService.validateThisStudent(token, userName);
		return this.notificationService.markAsViewed(userName, notificationId);
	}
	
	@RequestMapping(value="{notificationId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public @ResponseBody void deleteNotification(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @PathVariable Integer notificationId) {
		this.sessionService.validateThisStudent(token, userName);
		this.notificationService.delete(userName, notificationId);
	}
}


