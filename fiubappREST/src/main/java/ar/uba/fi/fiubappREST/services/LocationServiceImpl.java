package ar.uba.fi.fiubappREST.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.MateLocationConverter;
import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotAllowedToSearchMatesLocationsException;
import ar.uba.fi.fiubappREST.persistance.ConfigurationRepository;
import ar.uba.fi.fiubappREST.persistance.LocationRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;

@Service
public class LocationServiceImpl implements LocationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	private LocationRepository locationRepository;
	private StudentRepository studentRepository;
	private ConfigurationRepository configurationRepository;
	private MateLocationConverter converter;
			
	@Autowired
	public LocationServiceImpl(LocationRepository locationRepository, StudentRepository studentRepository, ConfigurationRepository configurationRepository, MateLocationConverter converter){
		this.locationRepository = locationRepository;
		this.studentRepository = studentRepository;
		this.configurationRepository = configurationRepository;
		this.converter = converter;
	}

	@Override
	public Location updateLocation(String userName, Location location) {
		LOGGER.info(String.format("Updating location for student with userName %s.", userName));
		this.verifyUserNameConfinguration(userName);
		Location savedLocation = this.locationRepository.findByUserName(userName);
		savedLocation.setLatitude(location.getLatitude());
		savedLocation.setLongitude(location.getLongitude());
		this.locationRepository.save(savedLocation);
		LOGGER.info(String.format("Location for student with userName %s as updated.", userName));
		return savedLocation;
	}

	@Override
	public List<MateLocationRepresentation> findMatesLocations(String userName, Double latitudeFrom, Double latitudeTo, Double longitudeFrom, Double longitudeTo) {
		LOGGER.info(String.format("Finding mates within area latFrom %s, latTo %s, longFrom %s, longTo: %s for student with userName %s.", latitudeFrom, latitudeTo, longitudeFrom, longitudeTo, userName));
		this.verifyUserNameConfinguration(userName);		
		Student student = this.studentRepository.findMatesWithinAreaByUser(userName, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo);
		List<MateLocationRepresentation> representations = new ArrayList<MateLocationRepresentation>();
		if(student !=null){			
			for (Student mateWithinArea : student.getMates()) {
				representations.add(this.converter.convert(mateWithinArea));			
			}
		}
		LOGGER.info(String.format("Mates within area latFrom %s, latTo %s, longFrom %s, longTo: %s for student with userName %s were found.", latitudeFrom, latitudeTo, longitudeFrom, longitudeTo, userName));
		return representations;
	}

	private void verifyUserNameConfinguration(String userName) {
		LocationConfiguration locationConfiguration = this.configurationRepository.findLocationConfigurationByUserName(userName);
		if(!locationConfiguration.getIsEnabled()){
			LOGGER.info(String.format("Student with userName %s is not allowed to do this operation because configuration is not enabled.", userName));
			throw new StudentNotAllowedToSearchMatesLocationsException(userName);
		}
		
	}
}
