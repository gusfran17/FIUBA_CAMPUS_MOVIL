package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer>{
	
	public List<Subject> findByCareerCodeAndUserName(Integer careerCode, String userName);
	
}
