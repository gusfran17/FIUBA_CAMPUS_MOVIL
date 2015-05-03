package ar.uba.fi.fiubappREST.services;

import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;

public interface ProfilePictureService {

	public void update(String userName, MultipartFile image);

	public ProfilePicture findByUserName(String userName);

}
