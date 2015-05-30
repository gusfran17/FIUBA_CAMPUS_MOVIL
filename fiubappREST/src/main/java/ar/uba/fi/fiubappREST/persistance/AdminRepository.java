package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {
	
}
