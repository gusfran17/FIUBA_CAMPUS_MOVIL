package ar.uba.fi.fiubappREST.services;

import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.WallConfiguration;

public interface ConfigurationService {

	public LocationConfiguration getLocationConfiguration(String userName);

	public LocationConfiguration updateLocationConfiguration(String userName, LocationConfiguration locationConfiguration);

	public WallConfiguration getWallConfiguration(String userName);

	public WallConfiguration updateWallConfiguration(String userName, WallConfiguration wallConfiguration);

}
