package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;

public interface LocationService {

	public Location updateLocation(String userName, Location location);

	public List<MateLocationRepresentation> findMatesLocations(String userName,	Double latitudeFrom, Double latitudeTo, Double longitudeFrom, Double longitudeTo);

}
