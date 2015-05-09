package ar.uba.fi.fiubappREST.converters;

import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;

@Component
public class MateLocationConverter {
		
	public MateLocationRepresentation convert(Student student){
		MateLocationRepresentation representation = new MateLocationRepresentation();
		representation.setUserName(student.getUserName());
		representation.setName(student.getName());
		representation.setLastName(student.getLastName());
		//TODO hacer luego del merge
//		representation.setProfilePicture(student.getC...)
		representation.setLatitude(student.getLocation().getLatitude());
		representation.setLongitude(student.getLocation().getLongitude());		
		return representation;
	}
	
	
}
