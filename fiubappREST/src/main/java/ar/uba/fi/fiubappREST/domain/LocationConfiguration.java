package ar.uba.fi.fiubappREST.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LOCATION_CONFIGURATION")
public class LocationConfiguration extends Configuration{
	
	private Double distanceInKm;

	public Double getDistanceInKm() {
		return distanceInKm;
	}

	public void setDistanceInKm(Double distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

}
