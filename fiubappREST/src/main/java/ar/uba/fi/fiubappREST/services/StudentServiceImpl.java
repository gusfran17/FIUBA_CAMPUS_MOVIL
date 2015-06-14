package ar.uba.fi.fiubappREST.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.StudentConverter;
import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Configuration;
import ar.uba.fi.fiubappREST.domain.Gender;
import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.domain.MonthlyApprovedStudentsInformation;
import ar.uba.fi.fiubappREST.domain.ProfilePicture;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.domain.StudentState;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.MonthlyStudentInformationRepository;
import ar.uba.fi.fiubappREST.persistance.ProfilePictureRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentStateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;
import ar.uba.fi.fiubappREST.utils.ApprovedStudentsReportUpdater;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	private StudentRepository studentRepository;
	private CareerRepository careerRepository;
	private ProfilePictureRepository profilePictureRepository;
	private StudentConverter studentConverter;
	private Md5PasswordEncoder passwordEncoder;
	private StudentProfileConverter studentProfileConverter;
	private MonthlyStudentInformationRepository monthlyStudentInformationRepository;
	private ApprovedStudentsReportUpdater reportUpdater;

	@Value("${configurations.defaultDistanceInKm}")
	private Double defaultDistanceInKm;
	
	@Value("classpath:defaultProfilePicture.png")
	private Resource defaultProfilePicture;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, CareerRepository careerRepository, ProfilePictureRepository profilePictureRepository, StudentConverter studentConverter, 
			Md5PasswordEncoder passwordEncoder, StudentProfileConverter studentProfileConverter, MonthlyStudentInformationRepository monthlyStudentInformationRepository, ApprovedStudentsReportUpdater reportUpdater){
		this.studentRepository = studentRepository;
		this.careerRepository = careerRepository;
		this.profilePictureRepository = profilePictureRepository;
		this.studentConverter = studentConverter;
		this.passwordEncoder = passwordEncoder;
		this.studentProfileConverter = studentProfileConverter;
		this.monthlyStudentInformationRepository = monthlyStudentInformationRepository;
		this.reportUpdater = reportUpdater;
	}

	@Override
	public Student create(StudentCreationRepresentation studentRepresentation) {
		Student student = this.getStudent(studentRepresentation);
		LOGGER.info(String.format("Creating student with userName %s and careerCode.", student.getUserName(), studentRepresentation.getCareerCode()));
		this.verifyUnusedUserName(student);
		if(!studentRepresentation.getIsExchangeStudent()){
			Career career = this.getCareer(studentRepresentation.getCareerCode());
			this.createStudentCareer(student, career);
		}

		this.setDefaultConfiguration(student);
		this.createLocation(student);
		student = studentRepository.save(student); 
		
		this.createDefaultProfileImage(student);

		LOGGER.info(String.format("Student with userName %s and careerCode %s was created.", student.getUserName(), studentRepresentation.getCareerCode()));
		return student;
	}
	
	private void createLocation(Student student) {
		Location location = new Location();
		location.setStudent(student);
		student.setLocation(location);		
	}

	private void setDefaultConfiguration(Student student) {
		LocationConfiguration locationConfiguration = new LocationConfiguration();
		locationConfiguration.setIsEnabled(false);
		locationConfiguration.setDistanceInKm(this.defaultDistanceInKm);
		locationConfiguration.setStudent(student);
		student.setConfigurations(new HashSet<Configuration>());
		student.getConfigurations().add(locationConfiguration);
	}
	
	private void createDefaultProfileImage(Student student) {
		ProfilePicture picture = new ProfilePicture();
		picture.setStudent(student);
		picture.setContentType(MediaType.IMAGE_PNG_VALUE);
		byte[] image;
		try {
			image = IOUtils.toByteArray(this.defaultProfilePicture.getInputStream());
			picture.setImage(image);
		} catch (IOException e) {
			LOGGER.error(String.format("File %s for profile picture for student with userName %s was not read.", this.defaultProfilePicture.getFilename(), student.getUserName()));
			throw new UnexpectedErrorReadingProfilePictureFileException(this.defaultProfilePicture.getFilename());
		}
		this.profilePictureRepository.save(picture);
	}

	private Student getStudent(StudentCreationRepresentation studentRepresentation){
		String encodedPassword = this.passwordEncoder.encodePassword(studentRepresentation.getPassword(), null);
		studentRepresentation.setPassword(encodedPassword);		
		return this.studentConverter.convert(studentRepresentation);
	}
	
	private void verifyUnusedUserName(Student student) {
		LOGGER.info(String.format("Verifying wether student with userName %s already exists.", student.getUserName()));
		Student foundStudent = this.studentRepository.findOne(student.getUserName());
		if(foundStudent != null){
			String identifierName = (student.getIsExchangeStudent()) ? "pasaporte" : "padrón";
			String identifierValue = (student.getIsExchangeStudent()) ? foundStudent.getPassportNumber() : foundStudent.getFileNumber();
			LOGGER.error(String.format("Student with userName %s already exists.", student.getUserName()));
			throw new StudentAlreadyExistsException(identifierName, identifierValue); 
		}		
		LOGGER.info(String.format("Student with userName %s doesn't exist.", student.getUserName()));
	}

	private Career getCareer(Integer careerCode) {
		LOGGER.info(String.format("Finding career with code %s.", careerCode));
		Career career = this.careerRepository.findByCode(careerCode);
		if(career == null){
			LOGGER.error(String.format("Career with code %s was not found.", careerCode));
			throw new CareerNotFoundException(careerCode);
		}
		LOGGER.info(String.format("Career with code %s was found.", careerCode));
		return career;
	}

	private StudentCareer createStudentCareer(Student student, Career career) {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);
		return studentCareer;
	}
	
	private Student findOneWithMates(String userName) {
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findByUserNameAndFetchMatesEagerly(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}
	
	@Override
	public List<StudentProfileRepresentation> findByProperties(String myUserName, String name, String lastName, 
			String email, String careerCode, String fileNumber, String passportNumber) {
		LOGGER.info(String.format("Finding students by criteria."));
		Student me = this.findOneWithMates(myUserName);
		List<Student> students = this.studentRepository.findByProperties(name, lastName, email, careerCode, fileNumber, passportNumber, null);
		this.removeMe(students, me);
		List<StudentProfileRepresentation> profiles = new ArrayList<StudentProfileRepresentation>();
		for (Student student : students) {
			profiles.add(this.studentProfileConverter.convert(me, student));
		}
		LOGGER.info(String.format("All students meeting the criteria were found."));
		return profiles;
	}

    private void removeMe(List<Student> students, Student me) {
		for (Student student : students) {
			if(me.getUserName().equals(student.getUserName())){
				students.remove(student);
				return;
			}
		}
	}

	@Override
	public Student findOne(String userName) {
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}

	@Override
	public Student update(String userName, StudentUpdateRepresentation studentRepresentation) {
		Student student = this.findOne(userName);
		LOGGER.info(String.format("Updating student with userName %s.", userName));
		this.updateStudent(student, studentRepresentation);
		student = this.studentRepository.save(student);
		LOGGER.info(String.format("Student with userName %s was updated.", userName));
		return student;
	}

	private void updateStudent(Student student, StudentUpdateRepresentation studentRepresentation) {
		String name = (studentRepresentation.getName()!=null) ? studentRepresentation.getName() : student.getName();
		student.setName(name);
		
		String lastName = (studentRepresentation.getLastName()!=null) ? studentRepresentation.getLastName() : student.getLastName();
		student.setLastName(lastName);
		
		Date dateOfBirth = (studentRepresentation.getDateOfBirth()!=null) ? studentRepresentation.getDateOfBirth() : student.getDateOfBirth(); 
		student.setDateOfBirth(dateOfBirth);
		
		String email = (studentRepresentation.getEmail()!=null) ? studentRepresentation.getEmail() : student.getEmail();
		student.setEmail(email);
		
		String phoneNumber = (studentRepresentation.getPhoneNumber()!=null) ? studentRepresentation.getPhoneNumber() : student.getPhoneNumber();
		student.setPhoneNumber(phoneNumber);
		
		String currentCity = (studentRepresentation.getCurrentCity()!=null) ? studentRepresentation.getCurrentCity() : student.getCurrentCity();
		student.setCurrentCity(currentCity);
		
		String nationality = (studentRepresentation.getNationality()!=null) ? studentRepresentation.getNationality() : student.getNationality();
		student.setNationality(nationality);
		
		String comments = (studentRepresentation.getComments()!=null) ? studentRepresentation.getComments() : student.getComments();
		student.setComments(comments);
		
		Gender gender = (studentRepresentation.getGender()!=null) ? studentRepresentation.getGender() : student.getGender();
		student.setGender(gender);
	}

	@Override
	public List<StudentProfileRepresentation> findByProperties(String name, String lastName, String fileNumber, String passportNumber, StudentState state) {
		LOGGER.info(String.format("Finding students by criteria."));
		Integer studentState = (state==null) ? null : state.getId();
		List<Student> students = this.studentRepository.findByProperties(name, lastName,null, null, fileNumber, passportNumber, studentState);
		List<StudentProfileRepresentation> profiles = new ArrayList<StudentProfileRepresentation>();
		for (Student student : students) {
			profiles.add(this.studentProfileConverter.convert(student));
		}
		LOGGER.info(String.format("All students meeting the criteria were found."));
		return profiles;
	}
	
	@Override
	public StudentStateRepresentation updateStudentState(String userName, StudentStateRepresentation stateRepresentation) {
		Student student = this.findOne(userName);
		LOGGER.info(String.format("Updating state for student with userName %s.", userName));
		student.setState(stateRepresentation.getState());
		student = this.studentRepository.save(student);
		stateRepresentation.setState(student.getState());
		this.countStudent(stateRepresentation.getState());
		LOGGER.info(String.format("State for sudent with userName %s was updated.", userName));
		return stateRepresentation;
	}

	private void countStudent(StudentState state) {
		this.reportUpdater.regenerateInformation();
		Pageable topOne = new PageRequest(0, 1);
		MonthlyApprovedStudentsInformation lastInformation = this.monthlyStudentInformationRepository.findByOrderByMonthYearDesc(topOne).get(0);
		if(state.equals(StudentState.APPROVED)){
			lastInformation.increaseAmountOfStudents();
		}else{
			lastInformation.decreaseAmountOfStudents();
		}
		this.monthlyStudentInformationRepository.save(lastInformation);
	}

	public Double getDefaultDistanceInKm() {
		return defaultDistanceInKm;
	}

	public void setDefaultDistanceInKm(Double defaultDistanceInKm) {
		this.defaultDistanceInKm = defaultDistanceInKm;
	}

	public Resource getDefaultProfilePicture() {
		return defaultProfilePicture;
	}

	public void setDefaultProfilePicture(Resource defaultProfilePicture) {
		this.defaultProfilePicture = defaultProfilePicture;
	}

}
