package com.fiubapp.fiubapp;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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

public class FragmentTab2 extends Fragment {
    ListView lv1;
    private TextView text;
    private static final String TAG = FragmentTab2.class.getSimpleName();

    private String urlAPI="";
    private ProgressDialog pDialog;
    private List<Alumno> alumnoList = new ArrayList<Alumno>();
    private ListView listView;
    private CustomListAdapter adapter;

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

        View view = inflater.inflate(R.layout.fragmenttab2, container, false);

        listView = (ListView)view.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), alumnoList);
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

                            alumno.setUsername(obj.getString("userName"));

                            //alumno.setYear(obj.getInt("releaseYear"));

                            // Genre is json array
                            /*JSONArray genreArry = obj.getJSONArray("genre");
                            ArrayList<String> genre = new ArrayList<String>();
                            for (int j = 0; j < genreArry.length(); j++) {
                                genre.add((String) genreArry.get(j));
                            }
                            movie.setGenre(genre);*/

                            // adding movie to movies array
                            alumnoList.add(alumno);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    // notifying list adapter about data changes
                    // so that it renders the list view with updated data
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
        return view;
    }

}