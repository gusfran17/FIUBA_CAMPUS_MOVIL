package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.GroupFile;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.GroupFileNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.GroupNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentIsNotMemberOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.persistance.GroupFileRepository;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class GroupFileServiceImplTest {
	
	private static final int A_FILE_ID = 12;

	private static final int MAX_SIZE_IN_BYTES = 10*1024;
	
	private static final int A_GROUP_ID = 10;

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private GroupFileRepository groupFileRepository;
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private Group group;
		
	private Student student;
		
	private GroupFileServiceImpl service;
	
	@Before
	public void setUp() throws ParseException{
		this.groupFileRepository = mock(GroupFileRepository.class);
		this.groupRepository = mock(GroupRepository.class);
		this.studentRepository = mock(StudentRepository.class);
		this.service= new GroupFileServiceImpl(groupFileRepository, groupRepository, studentRepository);
		this.service.setMaxSizeInBytes(MAX_SIZE_IN_BYTES);
		
		this.group = mock(Group.class);
		when(this.group.getId()).thenReturn(A_GROUP_ID);
		
		this.student = mock(Student.class);
		when(this.student.getUserName()).thenReturn(AN_USER_NAME);
		when(this.student.isMemberOf(group)).thenReturn(true);
		
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		
	}
		
	@Test
	public void testLoadFile() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(file.getBytes()).thenReturn("Mock".getBytes());
		when(file.getOriginalFilename()).thenReturn("Mock.png");
		when(this.groupFileRepository.save(any(GroupFile.class))).thenReturn(mock(GroupFile.class));
		
		GroupFile savedFile = this.service.loadFile(A_GROUP_ID, AN_USER_NAME, file);
						
		assertEquals(MediaType.IMAGE_PNG_VALUE, savedFile.getContentType());
		assertEquals(group, savedFile.getGroup());
		assertEquals("Mock.png", savedFile.getName());
	}
	
	@Test(expected = UnexpectedErrorReadingProfilePictureFileException.class)
	public void testLoadFileIOException() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(file.getBytes()).thenThrow(new IOException());
		
		this.service.loadFile(A_GROUP_ID, AN_USER_NAME, file);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void testLoadFileGroupNotFound() throws IOException {
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		MultipartFile file = mock(MultipartFile.class);
		
		this.service.loadFile(A_GROUP_ID, AN_USER_NAME, file);
	}
	
	@Test(expected = StudentIsNotMemberOfGroupException.class)
	public void testLoadFileStudentNotMember() throws IOException {
		when(this.student.isMemberOf(group)).thenReturn(false);
		MultipartFile file = mock(MultipartFile.class);
		
		this.service.loadFile(A_GROUP_ID, AN_USER_NAME, file);
	}

	@Test
	public void testFindGroupFile() {
		GroupFile file = mock(GroupFile.class);
		when(this.groupFileRepository.findByIdAndGroupId(A_FILE_ID, A_GROUP_ID)).thenReturn(file);
		
		GroupFile foundFile = this.service.findGroupFile(A_GROUP_ID, A_FILE_ID, AN_USER_NAME);
		
		assertEquals(file, foundFile);
	}
	
	@Test(expected= GroupFileNotFoundException.class)
	public void testFindGroupFileNotFound() {
		when(this.groupFileRepository.findByIdAndGroupId(A_FILE_ID, A_GROUP_ID)).thenReturn(null);
		
		this.service.findGroupFile(A_GROUP_ID, A_FILE_ID, AN_USER_NAME);
	}
	
	@Test
	public void testGetFiles() {
		GroupFile aFile = mock(GroupFile.class);
		GroupFile anotherFile = mock(GroupFile.class);
		GroupFile yetAnotherFile = mock(GroupFile.class);
		List<GroupFile> files = new ArrayList<GroupFile>();
		files.add(aFile);
		files.add(anotherFile);
		files.add(yetAnotherFile);
		when(this.groupFileRepository.findByGroupId(A_GROUP_ID)).thenReturn(files);
		
		List<GroupFile> foundFiles = this.service.getFiles(A_GROUP_ID, AN_USER_NAME);
		
		assertEquals(files, foundFiles);
	}
		
}

