package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Application;
import ar.uba.fi.fiubappREST.domain.Notification;

public interface NotificationService {

	public Application createApplicationNotification(String userName, Application application);

	public List<Notification> find(String userName, Boolean isViewed);

	public Notification markAsViewed(String userName, Integer notificationId);

	public void delete(String userName, Integer notificationId);

}
