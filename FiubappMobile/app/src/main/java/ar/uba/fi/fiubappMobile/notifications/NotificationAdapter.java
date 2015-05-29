package ar.uba.fi.fiubappMobile.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONObject;

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
            convertView = inflater.inflate(R.layout.application_notification, null);
        } else {
            Log.d("NotificationAdapter:", "Convertview NOT NULL");
        }
        this.populateApplicationNotification((ApplicationNotification) notifications.get(position), convertView, parent.getContext(), position);
        return convertView;
    }

    private void populateApplicationNotification(final ApplicationNotification notification, final View convertView, final Context context, final int position) {

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);
        TextView markAsViewed = (TextView) convertView.findViewById(R.id.viewed);

        name.setText(notification.getApplicant().getNombre() + " " + notification.getApplicant().getApellido());
        description.setText(R.string.application_notification_message);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        creationDate.setText(df.format(notification.getCreationDate()));

        thumbNail.setImageUrl(notification.getApplicant().getImgURL(),imageLoader);
        thumbNail.setDefaultImageResId(R.drawable.no_image);
        thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = notification.getApplicant().getImgURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            }
        });

        final Map<String, String> params = new HashMap<String, String>();
        params.put("userName", notification.getApplicant().getUsername());

        final Button acceptButton = (Button)convertView.findViewById(R.id.acceptButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {

                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                    buildNotificationsUrl(),
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = notification.getApplicant().getNombre() + " "
                                    + notification.getApplicant().getApellido() + " y vos ahora son compañeros!";
                            Popup.showText(context, message, Toast.LENGTH_LONG).show();
                            setViewedNotification(notification, context, position);
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            String responseBody = null;

                            try {
                                responseBody = new String( error.networkResponse.data, "utf-8" );
                                JSONObject jsonObject = new JSONObject( responseBody );
                                //Popup.showText(context, jsonObject.getString("message") , Toast.LENGTH_LONG).show();
                                setViewedNotification(notification,context, position);
                            } catch (Exception e) {
                            }
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

    private void setViewedNotification(final ApplicationNotification notification, final Context context, final int position) {

        final Map<String, String> params = new HashMap<String, String>();
        params.put("isViewed", "true");

        JsonObjectRequest jsonReqPut = new JsonObjectRequest(Request.Method.PUT,
                buildNotificationsUrlPut()+notification.getId(),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        notifications.remove(position);

                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String responseBody = null;

                        if (error.networkResponse.statusCode == 400){
                            notifications.remove(position);
                        }

                        notifyDataSetChanged();

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
        VolleyController.getInstance().addToRequestQueue(jsonReqPut);

    }

    private String buildNotificationsUrl(){
        DataAccess dataAccess = new DataAccess(activity);
        return urlAPI + "/students/" + dataAccess.getUserName() + "/mates";
    }

    private String buildNotificationsUrlPut(){
        DataAccess dataAccess = new DataAccess(activity);
        return urlAPI + "/students/" + dataAccess.getUserName() + "/notifications/";
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }
}