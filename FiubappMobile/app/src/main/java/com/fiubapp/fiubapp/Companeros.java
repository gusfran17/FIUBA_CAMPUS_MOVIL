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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class Companeros extends Fragment {
    ListView lv1;
    private TextView text;
    private static final String TAG = Companeros.class.getSimpleName();

    private String urlAPI="";
    private ProgressDialog pDialog;
    private List<Alumno> alumnoList = new ArrayList<Alumno>();
    private ListView listView;
    private AlumnoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isAdded()){
            urlAPI = getResources().getString(R.string.urlAPI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.companeros, container, false);

        listView = (ListView)view.findViewById(R.id.list);
        adapter = new AlumnoAdapter(getActivity(), alumnoList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);

        // Creating volley request obj
        JsonArrayRequest alumnoReq = new JsonArrayRequest(buildNotificationsUrl(),
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());

                    // Parsing json
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            Alumno companero = new Alumno();
                            companero.setNombre(obj.getString("name"));
                            companero.setApellido(obj.getString("lastName"));
                            companero.setIntercambio(obj.getBoolean("isExchangeStudent"));
                            companero.setIsMyMate(obj.getBoolean("isMyMate"));
                            companero.setUsername(obj.getString("userName"));
                            /*if (companero.isIntercambio()){
                                companero.setUsername(obj.getString("passportNumber"));
                            }else{
                                companero.setUsername(obj.getString("fileNumber"));
                            }*/

                            JSONArray JSONCareers = new JSONArray(obj.getString("careers"));
                            ArrayList<String> carreras = new ArrayList<>();

                            for(int j=0; j < JSONCareers.length(); j++){

                                String carrera = JSONCareers.getString(j);

                                carreras.add(carrera);
                            }
                            companero.setCarreras(carreras);

                            alumnoList.add(companero);

                        } catch (JSONException e) {
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

                    headers.put("Authorization", getToken());
                    return headers;

                }
            };

       // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(alumnoReq);
        return view;
    }

    private String buildNotificationsUrl(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return urlAPI + "/students/" + dataAccess.getUserName() + "/mates";
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getToken();
    }

}