package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {

	public List<Group> findByName(String name);
	
}
