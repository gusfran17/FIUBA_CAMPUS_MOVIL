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

	@Query(value = 	"SELECT *  "
			+ "FROM student s "
			+ "WHERE ( (?1 is null) or (?1='') or (LOWER(s.name) LIKE %?1) ) AND ( (?2 is null) or (?2='') or (LOWER(s.lastName) LIKE %?2) )"
			+ "AND ( (?3 is null) or (?3='') or (LOWER(s.email) LIKE %?3) )"
			+ "AND ( (?4 is null) or (?4='') or (s.userName in (SELECT userName FROM student_career sc WHERE sc.code = ?4)) )"
			+ "AND ( (?5 is null) or (?5='') or (LOWER(s.fileNumber) LIKE %?5) )"
			+ "AND ( (?6 is null) or (?6='') or (LOWER(s.passportNumber) LIKE %?6) )"
			, nativeQuery = true)
	public List<Student> findByProperties(String name, String lastName, String email, String careerCode, String fileNumber, String passportNumber);
	
	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.groups WHERE s.userName = ?1")
    public Student findByUserNameAndFetchGroupsEagerly(String userName);

}
