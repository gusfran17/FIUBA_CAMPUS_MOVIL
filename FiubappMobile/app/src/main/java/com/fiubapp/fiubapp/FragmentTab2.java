package com.fiubapp.fiubapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import ar.uba.fi.fiubappMobile.partners.StudentSearchTabs;

public class FragmentTab2 extends Fragment {
    ListView lv1;
    private TextView text;
    private static final String TAG = FragmentTab2.class.getSimpleName();

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

        View partnersTabView = inflater.inflate(R.layout.fragmenttab2, container, false);

        Button btnSearchStudents = (Button) partnersTabView.findViewById(R.id.button_search_students);

        btnSearchStudents.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FragmentActivity searchTabs = getActivity();
                Intent searchIntent = new Intent(searchTabs, StudentSearchTabs.class);
                try {
                    startActivity(searchIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listView = (ListView)partnersTabView.findViewById(R.id.list);
        adapter = new AlumnoAdapter(getActivity(), alumnoList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);

        // Creating volley request obj
        JsonArrayRequest alumnoReq = new JsonArrayRequest(urlAPI+"/students/",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());

                    // Parsing json
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            Alumno alumno = new Alumno();
                            alumno.setNombre(obj.getString("name"));
                            alumno.setApellido(obj.getString("lastName"));
                            alumno.setIntercambio(obj.getBoolean("isExchangeStudent"));

                            if (alumno.isIntercambio()){
                                alumno.setUsername(obj.getString("passportNumber"));
                            }else{
                                alumno.setUsername(obj.getString("fileNumber"));
                            }

                            JSONArray JSONCareers = new JSONArray(obj.getString("careers"));
                            ArrayList<String> carreras = new ArrayList<>();

                            for(int j=0; j < JSONCareers.length(); j++){

                                String carrera = JSONCareers.getString(j);

                                carreras.add(carrera);
                            }
                            alumno.setCarreras(carreras);

                            alumnoList.add(alumno);

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
                    SharedPreferences settings = getActivity().getSharedPreferences(
                            getResources().getString(R.string.prefs_name), 0);
                    String token = settings.getString("token",null);
                    headers.put("Authorization", token);
                    return headers;

                }
            };

       // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(alumnoReq);
        return partnersTabView;
    }

}