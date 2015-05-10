package ar.uba.fi.fiubappMobile.partners;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fiubapp.fiubapp.Alumno;
import com.fiubapp.fiubapp.AlumnoAdapter;
import com.fiubapp.fiubapp.PerfilTabsCompanero;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularSearch extends Fragment {
    private String urlAPI;
    private static final String TAG = RegularSearch.class.getSimpleName();
    private List<Alumno> studentList = new ArrayList<Alumno>();
    private ListView  studentsMeetCriteria;
    private AlumnoAdapter studentAdapter;
    private EditText edt_search_name;
    private EditText edt_search_lastname;
    private Button btnSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View regularSearchView = inflater.inflate(R.layout.regular_search, container, false);
        edt_search_name = (EditText) regularSearchView.findViewById(R.id.edt_search_name);
        edt_search_lastname = (EditText) regularSearchView.findViewById(R.id.edt_search_lastname);
        btnSearch = (Button) regularSearchView.findViewById(R.id.button_search);
        studentsMeetCriteria = (ListView) regularSearchView.findViewById(R.id.lst_regular_search);

        //studentsMeetCriteria.setVisibility(View.INVISIBLE);
        studentAdapter = new AlumnoAdapter(getActivity(), studentList);
        studentsMeetCriteria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = studentList.get(position).getNombre();
                String lastName = studentList.get(position).getApellido();
                String comments = studentList.get(position).getComentario();
                boolean isExchange = studentList.get(position).isIntercambio();
                boolean isMyMate = studentList.get(position).isMyMate();

                String userName = studentList.get(position).getUsername();

                Intent i = new Intent(getActivity(),PerfilTabsCompanero.class);
                i.putExtra("name",name);
                i.putExtra("lastName",lastName);
                i.putExtra("userName",userName);
                i.putExtra("comments",comments);
                i.putExtra("isMyMate",isMyMate);
                startActivity(i);
            }
        });
        studentsMeetCriteria.setAdapter(studentAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //studentsMeetCriteria.setVisibility(View.VISIBLE);
                fillStudentList();

            }
        });


        return regularSearchView;
    }

    private void fillStudentList() {
        studentList.clear();

        String name = edt_search_name.getText().toString();
        String lastname = edt_search_lastname.getText().toString();

        // Creating volley request obj
        String requestURL = Uri.parse(urlAPI+"/students")
                .buildUpon()
                .appendQueryParameter("name", name)
                .appendQueryParameter("lastName", lastname)
                .build().toString();
        JsonArrayRequest studentReq = new JsonArrayRequest(requestURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Alumno student = new Alumno();
                                student.setNombre(obj.getString("name"));
                                student.setApellido(obj.getString("lastName"));
                                student.setIntercambio(obj.getBoolean("isExchangeStudent"));
                                student.setIsMyMate(obj.getBoolean("isMyMate"));
                                student.setUsername(obj.getString("userName"));
                                student.setComentario(obj.getString("comments"));
                                student.setImgURL(obj.getString("profilePicture"));

                                JSONArray JSONCareers = new JSONArray(obj.getString("careers"));
                                ArrayList<String> carreras = new ArrayList<>();

                                for(int j=0; j < JSONCareers.length(); j++){

                                    String carrera = JSONCareers.getString(j);

                                    carreras.add(carrera);
                                }
                                student.setCarreras(carreras);

                                studentList.add(student);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (studentList.size()==0) {
                            Popup.showText(getActivity(), "No se encontraron alumnos que coincidan con la busqueda.", Toast.LENGTH_LONG).show();
                        }
                        studentAdapter.notifyDataSetChanged();
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
        VolleyController.getInstance().addToRequestQueue(studentReq);

    }
}
