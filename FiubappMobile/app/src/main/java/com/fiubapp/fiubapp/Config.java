package com.fiubapp.fiubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;
import ar.uba.fi.fiubappMobile.utils.Localizador;

public class Config extends Fragment {

    private String urlAPI = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isAdded()) {
            urlAPI = getResources().getString(R.string.urlAPI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.config, container, false);

        ListView list = (ListView) view.findViewById(R.id.listconfig);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        Intent i = new Intent(getActivity(), PerfilTabs.class);
                        startActivity(i);
                        break;

                    case 1:
                        wallConfiguration();
                        break;

                    case 2:
                        configurarLocalizacion();
                        break;

                    case 3:
                        logout();
                        break;
                }
            }
        });

        return view;
    }

    private void wallConfiguration() {
        LayoutInflater li = LayoutInflater.from(this.getActivity().getApplicationContext());
        View wallConfigView = li.inflate(R.layout.configurar_muro, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        alertDialogBuilder.setView(wallConfigView);

        final CheckBox cb_wall_configuration = (CheckBox)wallConfigView.findViewById(R.id.cb_wall_configuration);

        SharedPreferences settings = this.getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String isEnabledWall = settings.getString("isEnabledWall", "false");

        if(isEnabledWall.equals("false"))
            cb_wall_configuration.setChecked(false);
        else
            cb_wall_configuration.setChecked(true);


        alertDialogBuilder
                .setCancelable(false)
                .setTitle(getResources().getString(R.string.configurar_Muro))
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    saveWallConfiguration(String.valueOf(cb_wall_configuration.isChecked()));
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void saveWallConfiguration(String isEnabled) {
        SharedPreferences settings = this.getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("isEnabledWall", isEnabled.toLowerCase());
        editor.commit();

        boolean enabled = true;
        if(isEnabled.toLowerCase().equals("false"))
            enabled = false;

        saveWallConfiguration(enabled);


    }

    private void saveWallConfiguration(boolean isEnabled) {
        final SharedPreferences settings = this.getActivity().getSharedPreferences(this.getActivity().getResources().getString(R.string.prefs_name), 0);

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        String url = this.getString(R.string.urlAPI) + "/students/" + settings.getString("username", null) + "/configurations/wall";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("isEnabled", isEnabled);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                        } catch (Exception e) {
                            return;
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", settings.getString("token", null));
                return headers;
            }
        };

        queue.add(jsObjRequest);

    }

    private void logout(){

            new AlertDialog.Builder(this.getActivity())
                    .setTitle("¿Salir de Fiubapp?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            borrarToken();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

    }

    private void borrarToken(){

        String url = getURLAPI()+"/sessions/students/";

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //el response es null cuando se borra el token,
                        //entonces se redirige al login
                        if (error.networkResponse == null){

                            Intent i = new Intent(getActivity(), Login.class);
                            startActivity(i);

                        }else {
                            Popup.showText(getActivity(), "Ha ocurrido un error. " +
                                    "Probá nuevamente en unos minutos", Toast.LENGTH_LONG).show();
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

    private void configurarLocalizacion() {
        LayoutInflater li = LayoutInflater.from(this.getActivity().getApplicationContext());
        View crearGrupoView = li.inflate(R.layout.configurar_localizacion, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        alertDialogBuilder.setView(crearGrupoView);

        final EditText etMostrarHasta = (EditText)crearGrupoView.findViewById(R.id.etMostrarHasta);
        final CheckBox cbMostrarMiUbicacion = (CheckBox)crearGrupoView.findViewById(R.id.cbMostrarMiUbicacion);

        SharedPreferences settings = this.getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String isEnabledLocation = settings.getString("isEnabledLocalization", "false");

        if(isEnabledLocation.equals("true"))
            cbMostrarMiUbicacion.setChecked(false);
        else
            cbMostrarMiUbicacion.setChecked(true);


        alertDialogBuilder
                .setCancelable(false)
                .setTitle(getResources().getString(R.string.configurar_localizacion))
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                boolean tieneError = false;
                                if (cbMostrarMiUbicacion.isChecked()) {
                                    if (etMostrarHasta.getText().toString().equals("")) {
                                        Popup.showText(getActivity(), "La distancia tiene que ser mayor a 0 y menor a 5000", Toast.LENGTH_LONG).show();
                                        tieneError = true;
                                    } else {
                                        try {
                                            float numero = Float.parseFloat(etMostrarHasta.getText().toString());
                                            if (numero == 0 || numero > 5000) {
                                                Popup.showText(getActivity(), "La distancia tiene que ser mayor a 0 y menor a 5000", Toast.LENGTH_LONG).show();
                                                tieneError = true;
                                            }
                                        } catch (NumberFormatException e) {
                                            Popup.showText(getActivity(), "La distancia tiene que ser mayor a 0 y menor a 5000", Toast.LENGTH_LONG).show();
                                            tieneError = true;
                                        }
                                    }
                                }

                                if (!tieneError)
                                    guardarConfiguracion(etMostrarHasta.getText().toString(), String.valueOf(cbMostrarMiUbicacion.isChecked()));
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void guardarConfiguracion(String distanceInKm, String isEnabled){
        SharedPreferences settings = this.getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("isEnabledLocation", isEnabled.toLowerCase());
        editor.putString("distanceInKmLocation", distanceInKm);
        editor.commit();

        boolean habilitado = true;
        if(isEnabled.toLowerCase().equals("false"))
            habilitado = false;

        guardarConfiguracionDeLocalizacionEnServidor(habilitado, distanceInKm);

        if(isEnabled.toLowerCase().equals("true")){
            obtenerLocalizacion();
        }
        else{
            detenerLocalizacion();
        }
    }

    private void guardarConfiguracionDeLocalizacionEnServidor(boolean isEnabled, String distanceInKm){
        final SharedPreferences settings = this.getActivity().getSharedPreferences(this.getActivity().getResources().getString(R.string.prefs_name), 0);

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        String url = this.getString(R.string.urlAPI) + "/students/" + settings.getString("username", null) + "/configurations/location";

        float distancia = 0;
        if(!distanceInKm.equals(""))
            distancia = Float.parseFloat(distanceInKm);

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("isEnabled", isEnabled);
            jsonParams.put("distanceInKm", distancia);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                        } catch (Exception e) {
                            return;
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", settings.getString("token", null));
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    private void detenerLocalizacion() {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        Localizador localizador = new Localizador() {
            @Override
            public void cambioUbicacion(LatLng miUbicacion) {
            }
        };
        locationManager.removeUpdates(localizador.getInstance());
    }

    private void obtenerLocalizacion(){
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        Localizador localizador = new Localizador() {
            @Override
            public void cambioUbicacion(LatLng miUbicacion) {
                actualizarLocalizacionAlumno(miUbicacion);
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, localizador.getInstance());
    }

    private void actualizarLocalizacionAlumno(LatLng miUbicacion){

        if(!isAdded())
            return;

        final SharedPreferences settings = this.getActivity().getSharedPreferences(this.getActivity().getResources().getString(R.string.prefs_name), 0);

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        String url = this.getString(R.string.urlAPI) + "/students/" + getUsername() + "/location";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("latitude", miUbicacion.latitude);
            jsonParams.put("longitude", miUbicacion.longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", settings.getString("token",null));
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(this.getActivity());
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(this.getActivity());
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(this.getActivity());
        return dataAccess.getURLAPI();
    }

}

