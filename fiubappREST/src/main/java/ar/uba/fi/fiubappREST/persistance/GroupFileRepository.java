package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.GroupFile;

@Repository
public interface GroupFileRepository extends CrudRepository<GroupFile, Integer> {

	@Query(value = "SELECT * FROM group_file WHERE groupId = ?1", nativeQuery = true)
	public List<GroupFile> findByGroupId(Integer groupId);

	@Query(value = "SELECT * FROM group_file WHERE id = ?1 AND groupId = ?2", nativeQuery = true)
	public GroupFile findByIdAndGroupId(Integer fileId, Integer groupId);

}
