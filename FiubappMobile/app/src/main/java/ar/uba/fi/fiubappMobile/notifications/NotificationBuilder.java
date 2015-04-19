package ar.uba.fi.fiubappMobile.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marcelo on 4/19/15.
 */
public class NotificationBuilder {

    public static Notification build(JSONObject obj) throws JSONException, ParseException {
        String type = obj.getString("type");
        Notification notification = null;
        if(type.equals("ApplicationNotification")){
            notification = buildApplicationNotification(obj);
        }
        return notification;
    }

    private static ApplicationNotification buildApplicationNotification(JSONObject obj) throws JSONException, ParseException {
        ApplicationNotification notification = new ApplicationNotification();
        notification.setType(obj.getString("type"));
        notification.setId(obj.getInt("id"));
        String dateStr = obj.getString("creationDate");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date creationDate = sdf.parse(dateStr);
        notification.setCreationDate(creationDate);
        notification.setIsViewed(obj.getBoolean("isViewed"));
        notification.setApplicantUserName(obj.getString("applicantUserName"));
        notification.setApplicantName(obj.getString("applicantName"));
        notification.setApplicantLastName(obj.getString("applicantLastName"));
        return notification;
    }
}
