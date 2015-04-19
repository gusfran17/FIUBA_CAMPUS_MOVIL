package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
	
	public List<Student> findAll();
	
	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.mates WHERE s.userName = ?1")
    public Student findByUserNameAndFetchMatesEagerly(String userName);

}
