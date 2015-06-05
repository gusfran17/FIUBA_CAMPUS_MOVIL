package ar.uba.fi.fiubappREST.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

@Service
public class GroupFileServiceImpl implements GroupFileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupFileServiceImpl.class);
	
	private GroupFileRepository groupFileRepository;
	private GroupRepository groupRepository;
	private StudentRepository studentRepository;

	@Value("${file.maxSizeInBytes}")
	private long maxSizeInBytes;
		
	@Autowired
	public GroupFileServiceImpl(GroupFileRepository groupFileRepository, GroupRepository groupRepository, StudentRepository studentRepository){
		this.groupFileRepository = groupFileRepository;
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public GroupFile loadFile(Integer groupId, String studentUserName, MultipartFile file) {
		LOGGER.info(String.format("Saving file %s for group with id %s.", file.getName(), groupId));
		Group group = findGroup(groupId);
		this.verifyGroupMember(group, studentUserName);
		GroupFile groupFile = createFileGroup(groupId, file, group);
		this.groupFileRepository.save(groupFile);
		LOGGER.info(String.format("File %s was saved for group with id %s.", file.getName(), groupId));
		return groupFile;
	}

	private GroupFile createFileGroup(Integer groupId, MultipartFile file, Group group) {
		GroupFile groupFile = new GroupFile();
		groupFile.setGroup(group);
		groupFile.setContentType(file.getContentType());
		groupFile.setName(file.getOriginalFilename());
		try {
			groupFile.setFile(file.getBytes());
		} catch (IOException e) {
			LOGGER.error(String.format("File %s for group with id %s was not read.", file.getName(), groupId));
			throw new UnexpectedErrorReadingProfilePictureFileException(file.getName());
		}
		return groupFile;
	}

	private Group findGroup(Integer groupId) {
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", groupId ));
			throw new GroupNotFoundException(groupId);
		}
		return group;
	}
	
	private void verifyGroupMember(Group group, String userName) {
		Student student = this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(userName);
		if(!student.isMemberOf(group)){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentIsNotMemberOfGroupException(student.getUserName(), group.getName()); 
		}
	}
	
	@Override
	public GroupFile findGroupFile(Integer groupId, Integer fileId, String userName) {
		LOGGER.info(String.format("Finding file with id %s for group with id %s.", fileId, groupId));
		Group group = this.findGroup(groupId);
		this.verifyGroupMember(group, userName);
		GroupFile file = this.groupFileRepository.findByIdAndGroupId(fileId, groupId);
		if(file==null){
			LOGGER.error(String.format("File with id %s was not found for group with id %s.", fileId, groupId ));
			throw new GroupFileNotFoundException(fileId, groupId); 
		}		
		LOGGER.info(String.format("File with id %s was found for group with id %s.", fileId, groupId));
		return file;
	}
	
	@Override
	public List<GroupFile> getFiles(Integer groupId, String userName) {
		LOGGER.info(String.format("Finding all files for group with id %s.", groupId));
		Group group = this.findGroup(groupId);
		this.verifyGroupMember(group, userName);
		List<GroupFile> files = this.groupFileRepository.findAll();
		LOGGER.info(String.format("All files for group with id %s were found.", groupId));
		return files;
	}

	public long getMaxSizeInBytes() {
		return maxSizeInBytes;
	}

	public void setMaxSizeInBytes(long maxSizeInBytes) {
		this.maxSizeInBytes = maxSizeInBytes;
	}

}
