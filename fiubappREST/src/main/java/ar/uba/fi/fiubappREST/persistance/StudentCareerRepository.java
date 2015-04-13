package ar.uba.fi.fiubappREST.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.StudentCareer;

public interface StudentCareerRepository extends CrudRepository<StudentCareer, Integer>{
	
	@Query(value = "SELECT * FROM student_career WHERE code = ?1 AND userName = ?2", nativeQuery = true)
	public StudentCareer findByCodeAndUserName(Integer code, String userName);

}
