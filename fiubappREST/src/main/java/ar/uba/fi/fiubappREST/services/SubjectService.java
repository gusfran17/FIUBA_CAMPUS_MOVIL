package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Credits;
import ar.uba.fi.fiubappREST.domain.Subject;

public interface SubjectService {

	public Credits findAll(Integer careerCode, String userName);

}
