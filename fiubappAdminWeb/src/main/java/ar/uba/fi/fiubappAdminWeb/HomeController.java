package ar.uba.fi.fiubappAdminWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController{
	
	@RequestMapping(method = RequestMethod.GET)
	public String getHomePage(){
		return "home";
	}
	
    @RequestMapping(value="webapp/{resource}/{page}", method = RequestMethod.GET)
    public String getPage(@PathVariable String resource, @PathVariable String page) {
        return resource + "/" + page;
    }
}
