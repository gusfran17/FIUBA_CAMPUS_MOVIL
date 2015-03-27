package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.Carrear;

public interface CarrearRepository extends CrudRepository<Carrear, Integer>{
	
	public Carrear findByCode(Integer code);
	
	public List<Carrear> findAll();

}
