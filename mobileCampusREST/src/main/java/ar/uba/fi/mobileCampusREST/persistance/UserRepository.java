package ar.uba.fi.mobileCampusREST.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.mobileCampusREST.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
