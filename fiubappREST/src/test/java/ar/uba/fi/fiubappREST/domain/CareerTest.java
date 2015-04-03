package ar.uba.fi.fiubappREST.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.OrientationAlreadyExistsException;

public class CareerTest {
	
	private static final int CAREER_CODE = 10;
	private static final String CAREER_NAME = "Ingeniería en Informática";
	private static final String ORIENTATION_NAME = "Gestión Industrial de Sistemas";
	
	private Career career;
	
	@Before
	public void setUp(){
		this.career = new Career();
		this.career.setCode(CAREER_CODE);
		this.career.setName(CAREER_NAME);
		this.career.setOrientations(new ArrayList<Orientation>());
	}

	@Test
	public void testAddOrientationOK() {
		Orientation orientation = new Orientation();
		orientation.setName(ORIENTATION_NAME);
		
		this.career.addOrientation(orientation);
		
		assertFalse(this.career.getOrientations().isEmpty());
	}
	
	@Test(expected=OrientationAlreadyExistsException.class)
	public void testAddDuplicatedOrientation() {
		Orientation anOrientation = new Orientation();
		anOrientation.setName(ORIENTATION_NAME);
		this.career.addOrientation(anOrientation);
		Orientation anotherOrientation = new Orientation();
		anotherOrientation.setName(ORIENTATION_NAME);
		
		this.career.addOrientation(anotherOrientation);
	}

}
