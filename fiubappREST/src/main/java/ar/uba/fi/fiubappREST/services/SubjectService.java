package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.CareerProgress;

public interface SubjectService {

	public CareerProgress getCareerProgress(Integer careerCode, String userName);

}
