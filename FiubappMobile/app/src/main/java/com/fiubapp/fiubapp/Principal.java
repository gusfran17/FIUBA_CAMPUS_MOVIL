package com.fiubapp.fiubapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;
import ar.uba.fi.fiubappMobile.utils.Localizador;

public class Principal extends FragmentActivity {

    DataAccess dataAccess = new DataAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.icon_page);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //viewPager.setOffscreenPageLimit(1);

        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerPreferenciasConfiguracion();
    }

    public void obtenerLocalizacion(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Localizador localizador = new Localizador() {
            @Override
            public void cambioUbicacion(LatLng miUbicacion) {
                actualizarLocalizacionAlumno(miUbicacion);
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, localizador.getInstance());
    }

    private String getUsername(){
        return dataAccess.getUserName();
    }

    public void obtenerPreferenciasConfiguracion() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.getString(R.string.urlAPI) + "/students/" + getUsername() + "/configurations/location";

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String isEnabled = response.getString("isEnabled");
                            String distanceInKm = response.getString("distanceInKm");

                            SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("isEnabledLocation", isEnabled);
                            editor.putString("distanceInKmLocation", distanceInKm);
                            editor.commit();

                            if(isEnabled.toLowerCase().equals("true")){
                                obtenerLocalizacion();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", dataAccess.getToken());
                return headers;
            }
        };
        queue.add(jsObjRequest);
    }

    private void actualizarLocalizacionAlumno(LatLng miUbicacion){
        RequestQueue queue = Volley.newRequestQueue(this);

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
                headers.put("Authorization", dataAccess.getToken());
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }
}