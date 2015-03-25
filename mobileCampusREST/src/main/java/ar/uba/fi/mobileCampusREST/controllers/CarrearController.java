package ar.uba.fi.mobileCampusREST.controllers;

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

import ar.uba.fi.mobileCampusREST.domain.Carrear;
import ar.uba.fi.mobileCampusREST.domain.Orientation;
import ar.uba.fi.mobileCampusREST.services.CarrearService;

@Controller
@RequestMapping("carrears")
public class CarrearController {
	
	private CarrearService carrearService;
	
	@Autowired
	public CarrearController(CarrearService carrearService) {
		super();
		this.carrearService = carrearService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody Carrear addCarrear(@RequestBody Carrear carrear) {
		return carrearService.save(carrear);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Carrear> getCarrears() {
		return carrearService.findAll();
	}
	
	@RequestMapping(value="{code}",method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Carrear getCarrear(@PathVariable Integer code) {
		return carrearService.findByCode(code);
	}
	
	@RequestMapping(value="{code}/orientations",method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Orientation> getCarrearOrientations(@PathVariable Integer code) {
		Carrear carrear = carrearService.findByCode(code);
		return carrear.getOrientations();
	}

}
