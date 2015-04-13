package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.StudentCareer;

public interface StudentCareerService {

	public StudentCareer create(String userName, Integer code);

	public List<StudentCareer> findAll(String userName);

}
