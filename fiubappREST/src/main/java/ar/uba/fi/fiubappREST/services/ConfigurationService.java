package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.LocationConfiguration;

public interface ConfigurationService {

	public LocationConfiguration getLocationConfiguration(String userName);

	public LocationConfiguration updateLocationConfiguration(String userName, LocationConfiguration locationConfiguration);

}
