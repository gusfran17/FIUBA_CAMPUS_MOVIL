package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Carrear;

public interface CarrearService {
	
	public Carrear findByCode(Integer code);
	
	public List<Carrear> findAll();
	
	public Carrear save(Carrear carrear);
	
}
