package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.GroupPicture;

@Repository
public interface GroupPictureRepository extends CrudRepository<GroupPicture, Integer> {

	@Query(value = "SELECT * FROM group_picture WHERE groupId = ?1", nativeQuery = true)
	public GroupPicture findByGroupId(Integer id);

}
