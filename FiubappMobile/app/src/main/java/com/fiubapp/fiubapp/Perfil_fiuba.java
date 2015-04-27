package com.fiubapp.fiubapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.dominio.Carrera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class Perfil_fiuba extends Fragment {

    private ArrayList<Carrera> carrerasAlumno = new ArrayList<Carrera>();
    private ArrayList<Carrera> todasCarrerasDisponibles = new ArrayList<Carrera>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perfil_fiuba, container, false);

        final ImageView imgEditarCarreras = (ImageView)view.findViewById(R.id.imgEditarCarreras);

        //para mostrar el perfil de un alumno no contacto
        if (getArguments() != null) {

            if (!getArguments().getBoolean("isMyMate")) {

                RelativeLayout rel_layout_header = (RelativeLayout)view.findViewById(R.id.rel_fiuba);
                rel_layout_header.setVisibility(View.INVISIBLE);

            }else{
                imgEditarCarreras.setVisibility(View.INVISIBLE);
            }
        }

        imgEditarCarreras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Seleccione una carrera")
                        .setSingleChoiceItems(getNombreCarreras(), -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                setCarreraAlumno(todasCarrerasDisponibles.get(which));
                                dialog.dismiss();
                            }
                        });

                builder.create();
                builder.show();
            }
        });

        getCarrerasAlumno(getUsername());
        getTodasCarreras();
        return view;
    }

    public void crearSeccionCarreras() {
        ListView listCarreras = (ListView)getActivity().findViewById(R.id.listCarreras);
        CarreraAdapter carreraAdapter = new CarreraAdapter(this, getActivity(), carrerasAlumno);
        listCarreras.setAdapter(carreraAdapter);
    }

    public void eliminarCarrera(int posicion) {
        if(!tieneCarreraDisponible(carrerasAlumno.get(posicion).getCodigo()))
            todasCarrerasDisponibles.add(carrerasAlumno.get(posicion));

        borrarCarreraAlumno(Integer.parseInt(carrerasAlumno.get(posicion).getCodigo()));
    }

    public void borrarCarreraAlumno(int codigo){

        final SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/careers/" + codigo;

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.DELETE, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCarrerasAlumno(username);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getCarrerasAlumno(username);
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = settings.getString("token", null);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    public void getCarrerasAlumno(String username){

        final SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        //final String username = settings.getString("username", null);

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                getResources().getString(R.string.urlAPI) + "/students/" + username + "/careers",
                //new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        carrerasAlumno.clear();

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject carreraJSON = response.getJSONObject(i);

                                String codigo = carreraJSON.getString("careerCode");
                                String nombre = carreraJSON.getString("careerName");

                                Carrera carrera = new Carrera();

                                carrera.setCodigo(codigo);
                                carrera.setNombre(nombre);

                                carrera.setSePuedeEliminar(true);

                                // Si es o no es un amigo no puede eliminar las carreras de otro
                                if (getArguments() != null && (!getArguments().getBoolean("isMyMate") || getArguments().getBoolean("isMyMate"))) {
                                    carrera.setSePuedeEliminar(false);
                                }

                                carrerasAlumno.add(carrera);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        getTodasCarreras();

                        crearSeccionCarreras();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(Companeros.class.getSimpleName(), "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                String token = settings.getString("token", null);

                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);

                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);
    }

    public void setCarreraAlumno(Carrera carrera){

        final SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/careers/" + carrera.getCodigo();

        if(tieneCarreraDisponible(carrera.getCodigo()))
            todasCarrerasDisponibles.remove(carrera);

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCarrerasAlumno(username);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = settings.getString("token", null);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    public void getTodasCarreras(){

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                getResources().getString(R.string.urlAPI) + "/careers",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        todasCarrerasDisponibles.clear();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject carreraJSON = response.getJSONObject(i);

                                String codigo = carreraJSON.getString("code");
                                String nombre = carreraJSON.getString("name");

                                Carrera carrera = new Carrera();

                                carrera.setCodigo(codigo);
                                carrera.setNombre(nombre);

                                if(!tieneCarreraAlumno(codigo))
                                    todasCarrerasDisponibles.add(carrera);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        crearSeccionCarreras();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(Companeros.class.getSimpleName(), "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);
    }

    public boolean tieneCarreraDisponible(String codigo){
        boolean tieneCarrera = false;

        for (int i = 0; i < todasCarrerasDisponibles.size(); i++) {
            if (todasCarrerasDisponibles.get(i).getCodigo().equals(codigo)){
                tieneCarrera = true;
                break;
            }
        }

        return tieneCarrera;
    }

    public boolean tieneCarreraAlumno(String codigo){
        boolean tieneCarrera = false;

        for (int i = 0; i < carrerasAlumno.size(); i++) {
            if (carrerasAlumno.get(i).getCodigo().equals(codigo)){
                tieneCarrera = true;
                break;
            }
        }

        return tieneCarrera;
    }

    public String[] getNombreCarreras(){

        String[] nombreCarreras = new String[todasCarrerasDisponibles.size()];

        for (int i = 0; i < todasCarrerasDisponibles.size(); i++) {
            nombreCarreras[i] = todasCarrerasDisponibles.get(i).getNombre();
        }

        return nombreCarreras;
    }

    public static Perfil_fiuba newContact(Alumno companero) {

        Perfil_fiuba perfil = new Perfil_fiuba();

        Bundle args = new Bundle();
        args.putString("name",companero.getNombre());
        args.putString("lastName",companero.getApellido());
        args.putString("userName",companero.getUsername());
        args.putString("comments", companero.getComentario());
        args.putBoolean("isMyMate",companero.isMyMate());

        perfil.setArguments(args);

        return perfil;

    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

}