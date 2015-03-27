package ar.uba.fi.fiubappAdminWeb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
  
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView welcome() {
 	
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("name", "Marcelo");
 
		return model;
 
	}
 
}
