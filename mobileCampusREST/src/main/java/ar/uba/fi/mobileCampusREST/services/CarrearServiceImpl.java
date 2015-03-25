package ar.uba.fi.mobileCampusREST.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ar.uba.fi.mobileCampusREST.domain.Carrear;
import ar.uba.fi.mobileCampusREST.exceptions.CarrearAlreadyExistsException;
import ar.uba.fi.mobileCampusREST.exceptions.CarrearNotFoundException;
import ar.uba.fi.mobileCampusREST.persistance.CarrearRepository;

@Service
public class CarrearServiceImpl implements CarrearService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CarrearServiceImpl.class);
	
	private CarrearRepository carrearRepository;
		
	@Autowired
	public CarrearServiceImpl(CarrearRepository carrearRepository){
		this.carrearRepository = carrearRepository;
	}

	@Override
	public List<Carrear> findAll() {
		LOGGER.info(String.format("Finding all carrears."));
		List<Carrear> carrears = carrearRepository.findAll();
		LOGGER.info(String.format("All carrears were found."));
		return carrears;
	}

	@Override
	public Carrear findByCode(Integer code) {
		LOGGER.info(String.format("Finding carrear with code %s.", code));
		Carrear carrear = carrearRepository.findByCode(code);
		if(carrear == null){
			LOGGER.error(String.format("Carrear with code %s was not found.", code));
			throw new CarrearNotFoundException(code);
		}
		LOGGER.info(String.format("Carrear with code %s was found.", code));
		return carrear;
	}

	@Override
	public Carrear save(Carrear carrear) {
		LOGGER.info(String.format("Creating carrear with code %s and name.", carrear.getCode(), carrear.getName()));
		try{
			carrearRepository.save(carrear);
		} catch (DataIntegrityViolationException e){
			LOGGER.error(String.format("Carrear with code %s or name %s already exists.", carrear.getCode(), carrear.getName()));
			throw new CarrearAlreadyExistsException(carrear.getCode(), carrear.getName());
		}
		LOGGER.info(String.format("Carrear with code %s and name %s was saved.", carrear.getCode(), carrear.getName()));
		return carrear;
	}

}
