package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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

public class Carreras extends Activity{

    private ArrayList<Carrera> carrerasAlumno = new ArrayList<Carrera>();
    private ArrayList<Carrera> todasCarreras = new ArrayList<Carrera>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carreras);
        getCarrerasAlumno();
        getTodasCarreras();
    }

    public void crearSeccionCarreras() {
        ListView listCarreras = (ListView)findViewById(R.id.listCarreras);
        CarreraAdapter carreraAdapter = new CarreraAdapter(this, carrerasAlumno);
        listCarreras.setAdapter(carreraAdapter);
    }

    public void eliminarCarrera(int posicion) {
        borrarCarreraAlumno(Integer.parseInt(carrerasAlumno.get(posicion).getCodigo()));
    }

    public void borrarCarreraAlumno(int codigo){

        final SharedPreferences settings = this.getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/careers/" + codigo;

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.DELETE, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCarrerasAlumno();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getCarrerasAlumno();
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

    public void getCarrerasAlumno(){

        final SharedPreferences settings = this.getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

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

                                carrerasAlumno.add(carrera);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    crearSeccionCarreras();
                }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FragmentTab2.class.getSimpleName(), "Error: " + error.getMessage());
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

    public void setCarreraAlumno(int codigo){

        final SharedPreferences settings = this.getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/careers/" + codigo;

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getCarrerasAlumno();
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

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject carreraJSON = response.getJSONObject(i);

                                String codigo = carreraJSON.getString("code");
                                String nombre = carreraJSON.getString("name");

                                Carrera carrera = new Carrera();

                                carrera.setCodigo(codigo);
                                carrera.setNombre(nombre);

                                todasCarreras.add(carrera);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        crearSeccionCarreras();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FragmentTab2.class.getSimpleName(), "Error: " + error.getMessage());
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

    public void onClickImgEditarCarreras(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione una carrera")
                .setItems(getNombreCarreras(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    setCarreraAlumno(Integer.parseInt(todasCarreras.get(which).getCodigo()));
                    }
                });

        builder.create();
        builder.show();
    }

    public String[] getNombreCarreras(){

        String[] nombreCarreras = new String[todasCarreras.size()];

        for (int i = 0; i < todasCarreras.size(); i++) {
            nombreCarreras[i] = todasCarreras.get(i).getNombre();
        }

        return nombreCarreras;
    }
}
