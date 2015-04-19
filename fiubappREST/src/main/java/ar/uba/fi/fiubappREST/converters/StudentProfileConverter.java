package ar.uba.fi.fiubappREST.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

@Component
public class StudentProfileConverter {
	
	public StudentProfileRepresentation convert(Student student){
		StudentProfileRepresentation profile = new StudentProfileRepresentation();
		profile.setUserName(student.getUserName());
		profile.setName(student.getName());
		profile.setLastName(student.getLastName());
		profile.setIsExchangeStudent(student.getIsExchangeStudent());
		profile.setFileNumber(student.getFileNumber());
		profile.setPassportNumber(student.getPassportNumber());
		List<String> careers = new ArrayList<String>();
		for (StudentCareer career : student.getCareers()) {
			careers.add(career.getCareer().getName());
		}
		profile.setCareers(careers);
		return profile;
	}
	
	public StudentProfileRepresentation convert(Student me, Student mate){
		StudentProfileRepresentation profile = new StudentProfileRepresentation();
		profile.setUserName(mate.getUserName());
		profile.setName(mate.getName());
		profile.setLastName(mate.getLastName());
		profile.setIsExchangeStudent(mate.getIsExchangeStudent());
		profile.setFileNumber(mate.getFileNumber());
		profile.setPassportNumber(mate.getPassportNumber());
		List<String> careers = new ArrayList<String>();
		for (StudentCareer career : mate.getCareers()) {
			careers.add(career.getCareer().getName());
		}
		profile.setCareers(careers);
		profile.setIsMyMate(me.isMateWith(mate));
		return profile;
	}
}
