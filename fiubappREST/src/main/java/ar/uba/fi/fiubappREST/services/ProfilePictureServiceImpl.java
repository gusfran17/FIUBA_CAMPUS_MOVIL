package ar.uba.fi.fiubappREST.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.exceptions.UnsupportedMediaTypeForProfilePictureException;
import ar.uba.fi.fiubappREST.persistance.ProfilePictureRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class ProfilePictureServiceImpl implements ProfilePictureService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilePictureServiceImpl.class);
	
	private ProfilePictureRepository profilePictureRepository;
	private StudentRepository studentRepository;

	@Value("${file.maxSizeInBytes}")
	private long maxSizeInBytes;
		
	@Autowired
	public ProfilePictureServiceImpl(ProfilePictureRepository profilePictureRepository, StudentRepository studentRepository){
		this.profilePictureRepository = profilePictureRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public void update(String userName, MultipartFile image) {
		LOGGER.info(String.format("Saving file %s as profile picture for student with userName %s.", image.getName(), userName));
		this.validateFileContentType(image);
		Student student = this.studentRepository.findOne(userName);
		ProfilePicture picture = this.profilePictureRepository.findByUserName(userName);
		picture.setStudent(student);
		picture.setContentType(image.getContentType());
		try {
			picture.setImage(image.getBytes());
		} catch (IOException e) {
			LOGGER.error(String.format("File %s for profile picture for student with userName %s was not read.", image.getName(), userName));
			throw new UnexpectedErrorReadingProfilePictureFileException(image.getName());
		}
		LOGGER.info(String.format("File %s was saved as profile picture for student with userName %s.", image.getName(), userName));
		this.profilePictureRepository.save(picture);
	}

	private void validateFileContentType(MultipartFile image) {
		if(!image.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) 
				&& !image.getContentType().equals(MediaType.IMAGE_PNG_VALUE)){
			LOGGER.error(String.format("Media Type %s of file %s is not allowed for profile picture.", image.getContentType(), image.getName()));
			throw new UnsupportedMediaTypeForProfilePictureException(image.getContentType());
		}		
	}

	@Override
	public ProfilePicture findByUserName(String userName) {
		LOGGER.info(String.format("Finding profile picture for student with userName %s.", userName));
		ProfilePicture picture = this.profilePictureRepository.findByUserName(userName);
		if(picture==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}		
		LOGGER.info(String.format("Profile picture for student with userName %s was found.", userName));
		return picture;
	}

	public long getMaxSizeInBytes() {
		return maxSizeInBytes;
	}

	public void setMaxSizeInBytes(long maxSizeInBytes) {
		this.maxSizeInBytes = maxSizeInBytes;
	}

}
