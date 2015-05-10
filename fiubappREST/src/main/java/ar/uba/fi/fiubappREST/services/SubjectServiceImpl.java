package ar.uba.fi.fiubappREST.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.CareerProgress;
import ar.uba.fi.fiubappREST.domain.Subject;
import ar.uba.fi.fiubappREST.exceptions.SubjectsNotFoundForStudentAndCareerException;
import ar.uba.fi.fiubappREST.persistance.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCareerServiceImpl.class);

	private SubjectRepository subjectRepository;

	@Value("${career.totalCredits}")
	private Integer careerTotalCredits;

	@Autowired
	public SubjectServiceImpl(SubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}

	public CareerProgress getCareerProgress(Integer careerCode, String userName) {
		LOGGER.info(String.format("Finding subjects for careerCode %s and userName %s.", careerCode, userName));
		List<Subject> subjects = subjectRepository.findByCareerCodeAndUserName(careerCode, userName);
		if (subjects == null || subjects.isEmpty()) {
			LOGGER.error(String.format("Subject with careerCode %s and userName %s was not found.", careerCode, userName));
			throw new SubjectsNotFoundForStudentAndCareerException(careerCode, userName);
		}
		CareerProgress careerProgress = new CareerProgress(subjects, careerTotalCredits);
		LOGGER.info(String.format("All subjects for careerCode %s and userName %s were found.", careerCode, userName));
		return careerProgress;
	}

	public Integer getCareerTotalCredits() {
		return careerTotalCredits;
	}

	public void setCareerTotalCredits(Integer careerTotalCredits) {
		this.careerTotalCredits = careerTotalCredits;
	}

}
