package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.exceptions.UnsupportedMediaTypeForProfilePictureException;
import ar.uba.fi.fiubappREST.persistance.ProfilePictureRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class ProfilePictureServiceImplTest {
	
	private static final int MAX_SIZE_IN_BYTES = 10*1024;

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private ProfilePictureRepository profilePictureRepository;
	@Mock
	private StudentRepository studentRepository;
		
	private Student student;
		
	private ProfilePictureServiceImpl service;
	
	@Before
	public void setUp() throws ParseException{
		this.profilePictureRepository = mock(ProfilePictureRepository.class);
		this.studentRepository = mock(StudentRepository.class);
		this.service= new ProfilePictureServiceImpl(profilePictureRepository, studentRepository);
		this.service.setMaxSizeInBytes(MAX_SIZE_IN_BYTES);
		
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
	}
		
	@Test
	public void testUpdatePNG() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		ProfilePicture picture = mock(ProfilePicture.class);
		when(this.profilePictureRepository.findByUserName(AN_USER_NAME)).thenReturn(picture);
		when(this.profilePictureRepository.save(picture)).thenReturn(picture);
		
		this.service.update(AN_USER_NAME, image);
		
		assertTrue(true);
	}
	
	@Test
	public void testUpdateJPEG() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		ProfilePicture picture = mock(ProfilePicture.class);
		when(this.profilePictureRepository.findByUserName(AN_USER_NAME)).thenReturn(picture);
		when(this.profilePictureRepository.save(picture)).thenReturn(picture);
		
		this.service.update(AN_USER_NAME, image);
		
		assertTrue(true);
	}
	
	@Test(expected=UnsupportedMediaTypeForProfilePictureException.class)
	public void testUpdateNotAllowedContent() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_GIF_VALUE);
				
		this.service.update(AN_USER_NAME, image);
	}
	
	@Test(expected=UnexpectedErrorReadingProfilePictureFileException.class)
	public void testUpdateError() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);
		when(image.getBytes()).thenThrow(new IOException());
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		ProfilePicture picture = mock(ProfilePicture.class);
		when(this.profilePictureRepository.findByUserName(AN_USER_NAME)).thenReturn(picture);
		
		this.service.update(AN_USER_NAME, image);
		
		assertTrue(true);
	}
	
	@Test
	public void testFindByUserName() {
		ProfilePicture picture = mock(ProfilePicture.class);
		when(this.profilePictureRepository.findByUserName(AN_USER_NAME)).thenReturn(picture);
		
		ProfilePicture foundPicture = this.service.findByUserName(AN_USER_NAME);
		
		assertEquals(picture, foundPicture);
	}
	
	@Test(expected=StudentNotFoundException.class)
	public void testFindByUserNameNotFound() {
		when(this.profilePictureRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		
		this.service.findByUserName(AN_USER_NAME);
	}
	
}
