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
			+ "WHERE ( (?1 is null) or (?1='') or (LOWER(s.name) LIKE %?1%) )"
			+ "AND ( (?2 is null) or (?2='') or (LOWER(s.lastName) LIKE %?2%) )"
			+ "AND ( (?3 is null) or (?3='') or (LOWER(s.email) LIKE %?3%) )"
			+ "AND ( (?4 is null) or (?4='') or (s.userName in (SELECT userName FROM student_career sc WHERE sc.code = ?4)) )"
			+ "AND ( (?5 is null) or (?5='') or (LOWER(s.fileNumber) LIKE %?5%) )"
			+ "AND ( (?6 is null) or (?6='') or (LOWER(s.passportNumber) LIKE %?6%) )"
			+ "AND ( (?7 is null)  or (s.state = ?7) )"
			, nativeQuery = true)
	public List<Student> findByProperties(String name, String lastName, String email, String careerCode, String fileNumber, String passportNumber, Integer state);
	
	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.groups LEFT JOIN FETCH s.mates WHERE s.userName = ?1")
    public Student findByUserNameAndFetchMatesAndGroupsEagerly(String userName);
	
	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.mates AS m LEFT JOIN FETCH m.configurations AS c " +
			"WHERE s.userName = ?1 AND c.isEnabled = true " +
			"AND m.location.latitude > ?2 AND m.location.latitude < ?3 " +
			"AND m.location.longitude > ?4 AND m.location.longitude < ?5")
    public Student findMatesWithinAreaByUser(String userName, Double latitudeFrom, Double latitudeTo, Double longitudeFrom, Double longitudeTo);	

	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.jobs WHERE s.userName = ?1")
    public Student findByUserNameAndFetchJobsEagerly(String userName);
}
