package com.fiubapp.fiubapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.notifications.Notification;
import ar.uba.fi.fiubappMobile.notifications.NotificationAdapter;
import ar.uba.fi.fiubappMobile.notifications.NotificationBuilder;
import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class NotificationsTab extends Fragment {

    private static final String TAG = NotificationsTab.class.getSimpleName();

    private String urlAPI="";
    private List<Notification> notifications = new ArrayList<Notification>();
    private ListView listView;
    private NotificationAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isAdded()){
            urlAPI = getResources().getString(R.string.urlAPI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenttab2, container, false);

        listView = (ListView)view.findViewById(R.id.list);
        adapter = new NotificationAdapter(getActivity(), notifications);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);

        JsonArrayRequest notificationsReq = new JsonArrayRequest(this.buildNotificationsUrl(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Notification notification = NotificationBuilder.build(obj);
                                notifications.add(notification);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", DataAccess.getToken(getParentFragment()));
                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(notificationsReq);
        return view;
    }

    private String buildNotificationsUrl(){
        return urlAPI + "/students/" + DataAccess.getUserName(this) + "/notifications?viewed=false";
    }

}
