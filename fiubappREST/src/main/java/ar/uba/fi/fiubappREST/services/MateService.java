package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public interface MateService {

	public StudentProfileRepresentation becomeMates(String userName, String mateUserName);

	public List<StudentProfileRepresentation> getMates(String userName);

	public void deleteMate(String userName, String mateUserName);

}
