package ar.uba.fi.fiubappREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Orientation;
import ar.uba.fi.fiubappREST.services.CareerService;

@Controller
@RequestMapping("careers")
public class CareerController {
	
	private CareerService careerService;
	
	@Autowired
	public CareerController(CareerService careerService) {
		super();
		this.careerService = careerService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Career addCareer(@RequestBody Career career) {
		return careerService.save(career);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Career> getCareers() {
		return careerService.findAll();
	}
	
	@RequestMapping(value="{code}",method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Career getCareer(@PathVariable Integer code) {
		return careerService.findByCode(code);
	}
	
	@RequestMapping(value="{code}/orientations", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Orientation addOrientation(@PathVariable Integer code, @RequestBody Orientation orientation) {
		return careerService.addOrientation(code, orientation);
	}
	
	@RequestMapping(value="{code}/orientations", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Orientation> getOrientations(@PathVariable Integer code) {
		return careerService.findOrientations(code);
	}
	
}
