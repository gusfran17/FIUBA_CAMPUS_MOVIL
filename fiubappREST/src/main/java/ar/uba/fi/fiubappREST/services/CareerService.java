package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Orientation;

public interface CareerService {
	
	public Career findByCode(Integer code);
	
	public List<Career> findAll();
	
	public Career save(Career career);

	public Orientation addOrientation(Integer careerCode, Orientation orientation);
	
	public List<Orientation> findOrientations(Integer careerCode);
	
}
