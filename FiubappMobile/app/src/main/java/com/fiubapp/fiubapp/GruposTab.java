package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.dominio.Grupo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GruposTab extends Fragment {

    private ArrayList<Grupo> gruposAlumno = new ArrayList<Grupo>();
    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.grupos_tab, container, false);

        Button btnCrearGrupo = (Button)view.findViewById(R.id.btnCrearGrupo);

        btnCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCrear();
            }
        });

        getGrupos();

        return view;
    }

    public void setCrear(){
        LayoutInflater li = LayoutInflater.from(((FragmentActivity)context));

        View crearGrupoView = li.inflate(R.layout.crear_grupo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(((FragmentActivity)context));
        alertDialogBuilder.setView(crearGrupoView);

        final EditText editNombre = (EditText)crearGrupoView.findViewById(R.id.etNombre);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Crear grupo")
                .setPositiveButton("Crear",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (editNombre.getText().toString().replace(" ","").equals("")){
                                    Popup.showText(context,"El campo nombre de Grupo es obligatorio",Popup.LENGTH_LONG).show();
                                } else {
                                    Grupo grupo = new Grupo();
                                    grupo.setNombre(editNombre.getText().toString());
                                    crearGrupo(grupo);
                                }

                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void crearGrupo(Grupo grupo){

        SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String token = settings.getString("token", null);
        final String username = settings.getString("username", null);

        RequestQueue queue = Volley.newRequestQueue(((FragmentActivity)context));
        String url = this.getString(R.string.urlAPI) + "/groups";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("name", grupo.getNombre());
            jsonParams.put("description", "");
            jsonParams.put("ownerUserName", username);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!isAdded()) return;
                getGrupos();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //parseo la respuesta del server para obtener JSON
                        String body = null;
                        try {
                            body = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                            JSONObject JSONBody = new JSONObject(body);
                            String codigoError = JSONBody.getString("code");
                        } catch (JSONException e) {
                        } catch (UnsupportedEncodingException e) {
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    public void getGrupos(){

        final SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(getResources().getString(R.string.prefs_name), 0);

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                getResources().getString(R.string.urlAPI) + "/groups",
                //new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (!isAdded()) return;
                        gruposAlumno.clear();

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject grupoJSON = response.getJSONObject(i);

                                String id = grupoJSON.getString("id");
                                String nombre = grupoJSON.getString("name");

                                Grupo grupo = new Grupo();

                                grupo.setId(id);
                                grupo.setNombre(nombre);

                                gruposAlumno.add(grupo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        crearSeccionGrupos();
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

    public void crearSeccionGrupos() {
        ListView listGrupos = (ListView)((FragmentActivity)context).findViewById(R.id.listGrupos);
        GrupoAdapter grupoAdapter = new GrupoAdapter((FragmentActivity)context, gruposAlumno);
        listGrupos.setAdapter(grupoAdapter);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
    }
}