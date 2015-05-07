package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.persistance.ConfigurationRepository;

public class ConfigurationServiceImplTest {
	
	private static final Double DISTANCE_IN_KM = 15.0;
	
	private static final Double ANOTHER_DISTANCE_IN_KM = 25.0;

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private ConfigurationRepository configurationRepository;
	
	private LocationConfiguration locationConfiguration;
		
	private ConfigurationService service;
	
	@Before
	public void setUp() throws ParseException{
		this.configurationRepository = mock(ConfigurationRepository.class);		
		this.service= new ConfigurationServiceImpl(configurationRepository);
		
		this.locationConfiguration = new LocationConfiguration();
		this.locationConfiguration.setIsEnabled(false);
		this.locationConfiguration.setDistanceInKm(DISTANCE_IN_KM);
	}
		
	@Test
	public void testGetLocationConfiguration() {
		when(this.configurationRepository.findLocationConfigurationByUserName(AN_USER_NAME)).thenReturn(locationConfiguration);
		when(this.configurationRepository.save(locationConfiguration)).thenReturn(locationConfiguration);
		
		LocationConfiguration savedLocationConfiguration = this.service.getLocationConfiguration(AN_USER_NAME);
		
		assertEquals(locationConfiguration, savedLocationConfiguration);
	}
	


	@Test
	public void testUpdate() throws ParseException {
		LocationConfiguration savedLocationConfiguration = new LocationConfiguration();
		when(this.configurationRepository.findLocationConfigurationByUserName(AN_USER_NAME)).thenReturn(savedLocationConfiguration);
		when(this.configurationRepository.save(savedLocationConfiguration)).thenReturn(savedLocationConfiguration);
		
		LocationConfiguration locationConfiguration = new LocationConfiguration();
		locationConfiguration.setIsEnabled(true);
		locationConfiguration.setDistanceInKm(ANOTHER_DISTANCE_IN_KM);
		
		LocationConfiguration updatedLocationConfiguration = this.service.updateLocationConfiguration(AN_USER_NAME, locationConfiguration);
		
		assertEquals(true, updatedLocationConfiguration.getIsEnabled());
		assertEquals(ANOTHER_DISTANCE_IN_KM, updatedLocationConfiguration.getDistanceInKm());
	}
	
}
