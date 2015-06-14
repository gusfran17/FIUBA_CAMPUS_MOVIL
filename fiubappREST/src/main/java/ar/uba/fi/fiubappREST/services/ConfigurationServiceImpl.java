package ar.uba.fi.fiubappREST.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.WallConfiguration;
import ar.uba.fi.fiubappREST.persistance.ConfigurationRepository;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
	
	private ConfigurationRepository configurationRepository;
		
	@Autowired
	public ConfigurationServiceImpl(ConfigurationRepository configurationRepository){
		this.configurationRepository = configurationRepository;
	}
	
	@Override
	public LocationConfiguration getLocationConfiguration(String userName) {
		LOGGER.info(String.format("Finding location configuration for student with userName %s.", userName));
		LocationConfiguration locationConfiguration = this.configurationRepository.findLocationConfigurationByUserName(userName);
		LOGGER.info(String.format("Location Configuration for student with userName %s were found.", userName));
		return locationConfiguration;
	}

	@Override
	public LocationConfiguration updateLocationConfiguration(String userName, LocationConfiguration locationConfiguration) {
		LocationConfiguration savedLocationConfiguration = this.configurationRepository.findLocationConfigurationByUserName(userName);
		savedLocationConfiguration.setIsEnabled(locationConfiguration.getIsEnabled());
		savedLocationConfiguration.setDistanceInKm(locationConfiguration.getDistanceInKm());
		this.configurationRepository.save(savedLocationConfiguration);
		return savedLocationConfiguration;
	}

	@Override
	public WallConfiguration getWallConfiguration(String userName) {
		LOGGER.info(String.format("Finding wall configuration for student with userName %s.", userName));
		WallConfiguration wallConfiguration = this.configurationRepository.findWallConfigurationByUserName(userName);
		LOGGER.info(String.format("Wall Configuration for student with userName %s were found.", userName));
		return wallConfiguration;
	}

	@Override
	public WallConfiguration updateWallConfiguration(String userName, WallConfiguration wallConfiguration) {
		WallConfiguration savedWallConfiguration = this.configurationRepository.findWallConfigurationByUserName(userName);
		savedWallConfiguration.setIsEnabled(wallConfiguration.getIsEnabled());
		this.configurationRepository.save(savedWallConfiguration);
		return savedWallConfiguration;
	}
}
