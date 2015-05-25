package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;
import ar.uba.fi.fiubappREST.services.LocationService;
import ar.uba.fi.fiubappREST.services.SessionService;

@Controller
@RequestMapping("students/{userName}")
public class LocationController {	
	
	private LocationService locationService;
	private SessionService sessionService;
	
	@Autowired
	public LocationController(LocationService locationService, SessionService sessionService) {
		super();
		this.locationService = locationService;
		this.sessionService = sessionService;
	}
		
	@RequestMapping(value="location", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Location updateLocation(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestBody Location location) {
		this.sessionService.validateThisStudent(token, userName);
		return this.locationService.updateLocation(userName, location);
	}
	
	@RequestMapping(value="mates/locations", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<MateLocationRepresentation> getMatesLocation(@RequestHeader(value="Authorization") String token, @PathVariable String userName, @RequestParam(value="latitudeFrom", required=true) Double latitudeFrom, @RequestParam(value="latitudeTo", required=true) Double latitudeTo, 
				@RequestParam(value="longitudeFrom", required=true) Double longitudeFrom, @RequestParam(value="longitudeTo", required=true) Double longitudeTo) {
		this.sessionService.validateThisStudent(token, userName);
		return this.locationService.findMatesLocations(userName, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo);
	}
}


