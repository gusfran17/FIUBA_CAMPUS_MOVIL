package ar.uba.fi.fiubappMobile.groups;

import android.content.Intent;
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
import com.fiubapp.fiubapp.Alumno;
import com.fiubapp.fiubapp.AlumnoAdapter;
import com.fiubapp.fiubapp.PerfilTabs;
import com.fiubapp.fiubapp.PerfilTabsCompanero;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class MiembrosTab extends Fragment {

    private MiembroAdapter alumnoAdapter;
    private List<Alumno> alumnoList = new ArrayList<Alumno>();
    private ListView alumnoListView;
    private View view = null;
    private String urlAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.listado, container, false);

        alumnoListView = (ListView)view.findViewById(R.id.list);
        alumnoAdapter = new MiembroAdapter(getActivity(), alumnoList);
        alumnoListView.setAdapter(alumnoAdapter);

        alumnoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = alumnoList.get(position).getNombre();
                String lastName = alumnoList.get(position).getApellido();
                String comments = alumnoList.get(position).getComentario();
                boolean isExchange = alumnoList.get(position).isIntercambio();
                boolean isMyMate = alumnoList.get(position).isMyMate();

                String userName = alumnoList.get(position).getUsername();

                if (!userName.equals(getUsername())) {
                    Intent i = new Intent(getActivity(), PerfilTabsCompanero.class);
                    i.putExtra("name", name);
                    i.putExtra("lastName", lastName);
                    i.putExtra("userName", userName);
                    i.putExtra("comments", comments);
                    i.putExtra("isMyMate", isMyMate);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), PerfilTabs.class);
                    startActivity(i);
                }
            }
        });

        obtenerMiembros(getArguments().getInt("idGrupo"));

        return view;
    }

    private void obtenerMiembros(int idGrupo) {

        alumnoList.clear();
        // Creating volley request obj
        JsonArrayRequest alumnoReq = new JsonArrayRequest(getURLAPI()+"/groups/"+Integer.toString(idGrupo)+"/members",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(MiembrosTab.class.getSimpleName(), response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Alumno miembro = new Alumno();
                                miembro.setNombre(obj.getString("name"));
                                miembro.setApellido(obj.getString("lastName"));
                                miembro.setIntercambio(obj.getBoolean("isExchangeStudent"));
                                miembro.setIsMyMate(obj.getBoolean("isMyMate"));
                                miembro.setUsername(obj.getString("userName"));
                                miembro.setImgURL(obj.getString("profilePicture"));
                                miembro.setComentario(obj.getString("comments"));

                                JSONArray JSONCareers = new JSONArray(obj.getString("careers"));
                                ArrayList<String> carreras = new ArrayList<>();

                                for(int j=0; j < JSONCareers.length(); j++){

                                    String carrera = JSONCareers.getString(j);

                                    carreras.add(carrera);
                                }
                                miembro.setCarreras(carreras);

                                alumnoList.add(miembro);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        alumnoAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(MiembrosTab.class.getSimpleName(), "Error: " + error.getMessage());
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
    }

    public static MiembrosTab nuevoGrupo(int idGrupo) {
        MiembrosTab tab = new MiembrosTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo", idGrupo);
        tab.setArguments(args);
        return tab;
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getURLAPI();
    }

}