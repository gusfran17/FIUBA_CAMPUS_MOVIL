package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;

@Repository
public interface ProfilePictureRepository extends CrudRepository<ProfilePicture, Integer> {

	@Query(value = "SELECT * FROM picture WHERE userName = ?1", nativeQuery = true)
	public ProfilePicture findByUserName(String userName);

}
