package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.Career;

public interface CareerRepository extends CrudRepository<Career, Integer>{
	
	public Career findByCode(Integer code);
	
	public List<Career> findAll();

}
