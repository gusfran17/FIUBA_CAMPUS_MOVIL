package ar.uba.fi.mobileCampusREST.services;

import java.util.List;

import ar.uba.fi.mobileCampusREST.domain.Carrear;

public interface CarrearService {
	
	public Carrear findByCode(Integer code);
	
	public List<Carrear> findAll();
	
	public Carrear save(Carrear carrear);
	
}
