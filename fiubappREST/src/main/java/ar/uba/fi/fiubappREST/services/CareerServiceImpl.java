package ar.uba.fi.fiubappREST.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Orientation;
import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.OrientationRepository;

@Service
public class CareerServiceImpl implements CareerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CareerServiceImpl.class);
	
	private CareerRepository careerRepository;
	private OrientationRepository orientationRepository;
		
	@Autowired
	public CareerServiceImpl(CareerRepository careerRepository, OrientationRepository orientationRepository){
		this.careerRepository = careerRepository;
		this.orientationRepository = orientationRepository;
	}

	@Override
	public List<Career> findAll() {
		LOGGER.info(String.format("Finding all careers."));
		List<Career> careers = careerRepository.findAll();
		LOGGER.info(String.format("All careers were found."));
		return careers;
	}

	@Override
	public Career findByCode(Integer code) {
		LOGGER.info(String.format("Finding career with code %s.", code));
		Career career = careerRepository.findByCode(code);
		if(career == null){
			LOGGER.error(String.format("Career with code %s was not found.", code));
			throw new CareerNotFoundException(code);
		}
		LOGGER.info(String.format("Career with code %s was found.", code));
		return career;
	}

	@Override
	public Career save(Career career) {
		LOGGER.info(String.format("Creating career with code %s and name.", career.getCode(), career.getName()));
		if(this.exists(career)){
			LOGGER.error(String.format("Carreer with code %s already exists.", career.getCode()));
			throw new CareerAlreadyExistsException(career.getCode());
		}
		try{
			careerRepository.save(career);
		} catch(DataIntegrityViolationException e){
			LOGGER.error(String.format("Career with name %s already exists.", career.getName()));
			throw new CareerAlreadyExistsException(career.getName());
		}
		LOGGER.info(String.format("Career with code %s and name %s was saved.", career.getCode(), career.getName()));
		return career;
	}

	private boolean exists(Career career) {
		return this.careerRepository.exists(career.getCode());
	}

	@Override
	public Orientation addOrientation(Integer careerCode, Orientation orientation) {
		Career career = this.findByCode(careerCode);
		LOGGER.info(String.format("Creating orientation with name %s for career %s.", orientation.getName(), career.getCode()));
		career.addOrientation(orientation);
		this.orientationRepository.save(orientation);
		LOGGER.info(String.format("Orientation with name %s for career %s was saved.", orientation.getName(), career.getCode()));
		return orientation;
	}

	@Override
	public List<Orientation> findOrientations(Integer careerCode) {
		this.findByCode(careerCode);
		LOGGER.info(String.format("Finding all orientations for career with code %s.", careerCode));
		List<Orientation> orientations = this.orientationRepository.findByCode(careerCode);
		LOGGER.info(String.format("All orientations for career with code %s were found.", careerCode));
		return orientations;
	}
}
