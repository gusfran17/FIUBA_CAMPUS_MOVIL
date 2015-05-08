package ar.uba.fi.fiubappREST.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Credits;
import ar.uba.fi.fiubappREST.domain.Subject;
import ar.uba.fi.fiubappREST.exceptions.SubjectsNotFoundForStudentAndCareerException;
import ar.uba.fi.fiubappREST.persistance.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCareerServiceImpl.class);
	
	private SubjectRepository subjectRepository;
	
	@Autowired
	public SubjectServiceImpl(SubjectRepository subjectRepository){
		this.subjectRepository = subjectRepository;
	}
	
	public Credits findAll(Integer careerCode, String userName) {
		List<Subject> subjects = subjectRepository.findByUserNameAndCareerCode(careerCode,userName);
		if(subjects == null){
			LOGGER.error(String.format("Subject with careerCode %s and userName %s was not found.", careerCode, userName));
			throw new SubjectsNotFoundForStudentAndCareerException(careerCode, userName);
		}
		if (subjects.size()==0){
			LOGGER.error(String.format("Subject with careerCode %s and userName %s was not found.", careerCode, userName));
			throw new SubjectsNotFoundForStudentAndCareerException(careerCode, userName);
		}
		Integer totalAmount = 240;
		Credits credits = new Credits(subjects, totalAmount);
		return credits;
	}

}
