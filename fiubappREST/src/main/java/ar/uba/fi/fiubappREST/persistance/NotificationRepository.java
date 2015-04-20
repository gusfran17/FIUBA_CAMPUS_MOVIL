package ar.uba.fi.fiubappREST.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.uba.fi.fiubappREST.domain.ApplicationNotification;
import ar.uba.fi.fiubappREST.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer>{
	
	@Query(value = "SELECT * FROM notification WHERE id = ?1 AND userName = ?2", nativeQuery = true)
	public List<Notification> findByIdAndUserName(Integer id, String userName);
	
	@Query(value = "SELECT * FROM notification WHERE userName = ?1 ORDER BY creationDate DESC", nativeQuery = true)
	public List<Notification> findByUserName(String userName);
	
	@Query(value = "SELECT * FROM notification WHERE userName = ?1 AND isViewed = ?2 ORDER BY creationDate DESC", nativeQuery = true)
	public List<Notification> findByUserNameAndIsViewed(String userName, Boolean isViewed);
	
	@Query(value = "SELECT * FROM notification WHERE userName = ?1 AND applicantUserName = ?2", nativeQuery = true)
	public List<ApplicationNotification> findByUserNameAndApplicantUSerName(String userName, String applicantUserName);
	
}
