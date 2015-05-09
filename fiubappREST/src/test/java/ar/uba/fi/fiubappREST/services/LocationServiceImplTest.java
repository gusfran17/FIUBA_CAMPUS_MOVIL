package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.converters.MateLocationConverter;
import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotAllowedToSearchMatesLocationsException;
import ar.uba.fi.fiubappREST.persistance.ConfigurationRepository;
import ar.uba.fi.fiubappREST.persistance.LocationRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;

public class LocationServiceImplTest {
	
	private static final Double LONGITUDE = -58.123456;

	private static final Double LATITUDE = -34.123456;

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private LocationRepository locationRepository;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private ConfigurationRepository configurationRepository;
	@Mock
	private MateLocationConverter converter;
	@Mock
	private Student student;
	@Mock
	private LocationConfiguration configuration;
	@Mock
	private Location location;
	
			
	private LocationService service;
	
	@Before
	public void setUp() throws ParseException{
		this.locationRepository = mock(LocationRepository.class);
		this.studentRepository = mock(StudentRepository.class);
		this.configurationRepository = mock(ConfigurationRepository.class);		
		this.converter = mock(MateLocationConverter.class);
		this.service= new LocationServiceImpl(locationRepository, studentRepository, configurationRepository, converter);
		
		this.student = mock(Student.class);
		this.configuration = mock(LocationConfiguration.class);
		when(configuration.getIsEnabled()).thenReturn(true);
		this.location = new Location();
	}
		
	@Test
	public void testUpdateLocation() {
		when(this.configurationRepository.findLocationConfigurationByUserName(AN_USER_NAME)).thenReturn(configuration);
		when(this.locationRepository.findByUserName(AN_USER_NAME)).thenReturn(location);
		Location newLocation = new Location();
		newLocation.setLatitude(LATITUDE);
		newLocation.setLongitude(LONGITUDE);		
		when(this.locationRepository.save(location)).thenReturn(location);
		
		Location updatedLocation = this.service.updateLocation(AN_USER_NAME, newLocation);
		
		assertEquals(LATITUDE, updatedLocation.getLatitude());
		assertEquals(LONGITUDE, updatedLocation.getLongitude());
	}
	
	@Test(expected= StudentNotAllowedToSearchMatesLocationsException.class)
	public void testUpdateLocationError() {
		when(configuration.getIsEnabled()).thenReturn(false);
		when(this.configurationRepository.findLocationConfigurationByUserName(AN_USER_NAME)).thenReturn(configuration);
		Location newLocation = new Location();
		newLocation.setLatitude(LATITUDE);
		newLocation.setLongitude(LONGITUDE);		
		
		this.service.updateLocation(AN_USER_NAME, newLocation);
	}
	
	@Test
	public void testFindMatesLocations() {
		when(this.configurationRepository.findLocationConfigurationByUserName(AN_USER_NAME)).thenReturn(configuration);
		when(this.studentRepository.findMatesWithinAreaByUser(AN_USER_NAME, LATITUDE-10,LATITUDE+10, LONGITUDE-10, LONGITUDE+10)).thenReturn(student);
		List<Student> mates = new ArrayList<Student>();
		Student aMate = mock (Student.class);
		Student anotherMate = mock (Student.class);
		mates.add(aMate);
		mates.add(anotherMate);
		when(student.getMates()).thenReturn(mates);
		MateLocationRepresentation aMateLocation = mock(MateLocationRepresentation.class);
		MateLocationRepresentation anotherMateLocation = mock(MateLocationRepresentation.class);
		when(this.converter.convert(aMate)).thenReturn(aMateLocation);
		when(this.converter.convert(anotherMate)).thenReturn(anotherMateLocation);
		
		List<MateLocationRepresentation> locations = this.service.findMatesLocations(AN_USER_NAME, LATITUDE-10, LATITUDE+10, LONGITUDE-10, LONGITUDE+10);
		
		assertEquals(aMateLocation, locations.get(0));
		assertEquals(anotherMateLocation, locations.get(1));
	}
	
}
