package ar.uba.fi.fiubappREST.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.GroupFile;

public interface GroupFileService {

	public GroupFile loadFile(Integer groupId, String studentUserName, MultipartFile file);

	public GroupFile findGroupFile(Integer groupId, Integer fileId, String userName);

	public List<GroupFile> getFiles(Integer groupId, String userName);

}
