package ar.uba.fi.fiubappMobile.notifications;

import com.fiubapp.fiubapp.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        }else if (type.equals("DiscussionMessageNotification")){
            notification = buildDiscussionNotification(obj);
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
        JSONObject applicantObj = obj.getJSONObject("applicant");
        Alumno applicant = new Alumno();
        applicant.setUsername(applicantObj.getString("userName"));
        applicant.setNombre(applicantObj.getString("name"));
        applicant.setApellido(applicantObj.getString("lastName"));
        applicant.setIntercambio(applicantObj.getBoolean("isExchangeStudent"));
        applicant.setPasaporte(applicantObj.getString("passportNumber"));
        applicant.setImgURL(applicantObj.getString("profilePicture"));

        String padron = applicantObj.getString("fileNumber");
        if(padron != null && !padron.equals("null") && !padron.equals("") )
            applicant.setPadron(Integer.parseInt(padron));

        applicant.setComentario(applicantObj.getString("comments"));
        JSONArray careersObj = new JSONArray(applicantObj.getString("careers"));
        ArrayList<String> careers = new ArrayList<>();
        for(int j=0; j < careersObj.length(); j++){

            careers.add(careersObj.getString(j));
        }
        applicant.setCarreras(careers);
        applicant.setIsMyMate(applicantObj.getBoolean("isMyMate"));
        applicant.setComentario(applicantObj.getString("comments"));
        notification.setApplicant(applicant);
        return notification;
    }

    private static DiscussionNotification buildDiscussionNotification(JSONObject obj) throws JSONException, ParseException {

        DiscussionNotification notification = new DiscussionNotification();
        notification.setType(obj.getString("type"));
        notification.setId(obj.getInt("id"));

        String dateStr = obj.getString("creationDate");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date creationDate = sdf.parse(dateStr);
        notification.setCreationDate(creationDate);

        notification.setIsViewed(obj.getBoolean("isViewed"));
        notification.setGroupId(obj.getString("groupID"));
        notification.setDiscussionId(obj.getString("discussionID"));
        notification.setDiscussionName(obj.getString("discussionName"));
        notification.setDiscussionMessageId("discussionMessageId");

        String dateDiscussion = obj.getString("creationDate");
        SimpleDateFormat sdfDiscussion = new SimpleDateFormat("dd/MM/yyyy");
        Date creationDateDiscussion = sdfDiscussion.parse(dateDiscussion);
        notification.setCreationDate(creationDateDiscussion);

        JSONObject commenterObj = obj.getJSONObject("commenter");

        Alumno commenter = new Alumno();
        commenter.setUsername(commenterObj.getString("userName"));
        commenter.setNombre(commenterObj.getString("name"));
        commenter.setApellido(commenterObj.getString("lastName"));
        commenter.setIntercambio(commenterObj.getBoolean("isExchangeStudent"));
        commenter.setPasaporte(commenterObj.getString("passportNumber"));
        commenter.setImgURL(commenterObj.getString("profilePicture"));

        String padron = commenterObj.getString("fileNumber");
        if(padron != null && !padron.equals("null") && !padron.equals("") )
            commenter.setPadron(Integer.parseInt(padron));

        commenter.setComentario(commenterObj.getString("comments"));
        JSONArray careersObj = new JSONArray(commenterObj.getString("careers"));
        ArrayList<String> careers = new ArrayList<>();
        for(int j=0; j < careersObj.length(); j++){

            careers.add(careersObj.getString(j));
        }
        commenter.setCarreras(careers);
        commenter.setIsMyMate(commenterObj.getBoolean("isMyMate"));
        notification.setCommenter(commenter);

        return notification;
    }
}
