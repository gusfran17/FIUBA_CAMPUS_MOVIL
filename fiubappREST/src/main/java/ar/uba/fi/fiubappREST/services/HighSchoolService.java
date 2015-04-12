package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.HighSchool;

public interface HighSchoolService {
		
	public HighSchool create(String userName, HighSchool highSchool);

	public HighSchool findByUserName(String userName);

	public void delete(String userName);
	
}
