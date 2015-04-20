package ar.uba.fi.fiubappMobile.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fiubapp.fiubapp.EmailValidator;
import com.fiubapp.fiubapp.Login;
import com.fiubapp.fiubapp.Perfil;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.Register1;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class NotificationAdapter extends BaseAdapter {
    private String urlAPI;
    private Activity activity;
    private LayoutInflater inflater;
    private List<Notification> notifications;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public NotificationAdapter(Activity activity, List<Notification> notifications, String urlApi) {
        this.activity = activity;
        this.notifications = notifications;
        this.urlAPI = urlApi;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int location) {
        return notifications.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            if (notifications.get(position).getClass().equals(ApplicationNotification.class)) {
                convertView = inflater.inflate(R.layout.application_notification, null);
                this.populateApplicationNotification((ApplicationNotification) notifications.get(position), convertView, parent.getContext());
            }
        }

        return convertView;
    }

    private void populateApplicationNotification(final ApplicationNotification notification, final View convertView, final Context context) {
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);
        TextView markAsViewed = (TextView) convertView.findViewById(R.id.viewed);

        name.setText(notification.getApplicantName() + " " + notification.getApplicantLastName());
        description.setText(R.string.application_notification_message);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        markAsViewed.setText(R.string.application_notification_mark_as_viewed);
        creationDate.setText(df.format(notification.getCreationDate()));

        final Map<String, String> params = new HashMap<String, String>();
        params.put("userName", notification.getApplicantUserName());

        final Button acceptButton = (Button)convertView.findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                    buildNotificationsUrl(),
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Popup.showText(context, "Anduvo todo joya", Toast.LENGTH_LONG);
                            String message = notification.getApplicantName() + " " + notification.getApplicantLastName() + " y vos ahora son compañeros!";
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle("Agregar compañeros");
                            alertDialogBuilder
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Popup.showText(context, "Anduvo mal", Toast.LENGTH_LONG);
                        }
                    }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", getToken());
                        return headers;
                    }

                };
                VolleyController.getInstance().addToRequestQueue(jsonReq);
            }

        });
    }

    private String buildNotificationsUrl(){
        DataAccess dataAccess = new DataAccess(activity);
        return urlAPI + "/students/" + dataAccess.getUserName() + "/mates";
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }
}
