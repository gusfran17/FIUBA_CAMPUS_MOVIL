package com.fiubapp.fiubapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;
import ar.uba.fi.fiubappMobile.utils.Localizador;

public class CompanerosCercanos extends FragmentActivity implements OnMapReadyCallback {

    LatLng miUbicacion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.ver_carcanos);

        ImageView imageBack = (ImageView)findViewById(R.id.back_search);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dibujarTodo();
            }
        });

        configurarInfoMarker();
    }

    protected void onResume() {
        super.onResume();
        dibujarTodo();
    }

    public void dibujarTodo(){
        getMap().clear();

        miUbicacion = obtenerMiUbicacion();
        obtenerMisDatos();

        CameraUpdate center= CameraUpdateFactory.newLatLng(miUbicacion);
        CameraUpdate zoom;
        zoom = CameraUpdateFactory.zoomTo(14);

        getMap().moveCamera(center);
        getMap().animateCamera(zoom);
    }

    private void obtenerMisDatos(){

        SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        String distanceInKmLocation = settings.getString("distanceInKmLocation", "1");

        final int distancia = Math.round(1000 * Float.valueOf(distanceInKmLocation));

        DataAccess dataAccess = new DataAccess(this);

        String name = "";
        String lastname = "";

        String requestURL = Uri.parse(this.getString(R.string.urlAPI) + "/students/" + dataAccess.getUserName())
                .buildUpon()
                .build().toString();

        JsonObjectRequest studentReq = new JsonObjectRequest(requestURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String lastName = response.getString("lastName");
                            String userName = response.getString("userName");

                            agregarCompaniero("", String.valueOf(miUbicacion.latitude), String.valueOf(miUbicacion.longitude), userName, name, lastName, "true");

                            getMap().getUiSettings().setZoomControlsEnabled(true);

                            Circle circle = getMap().addCircle(new CircleOptions()
                                    .center(miUbicacion)
                                    .radius(distancia)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(0x404099ff)
                                    .strokeWidth(2));

                            dibujarCompanieros();

                        } catch (Exception e) {}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);
                return headers;
            }
        };

        VolleyController.getInstance().addToRequestQueue(studentReq);
    }

    public LatLng obtenerMiUbicacion(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Localizador localizador = new Localizador() {
            @Override
            public void cambioUbicacion(LatLng miUbicacion) {
            }
        };

        return localizador.getMiUbicacion();
    }

    public void dibujarCompanieros(){

        SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        String distanceInKmLocation = settings.getString("distanceInKmLocation", "1");
        String username = settings.getString("username", null);

        final int distanciaOriginal = Math.round(1000 * Float.valueOf(distanceInKmLocation));

        // Agrego por un posible error de 300 metros
        int distanciaConDelta = distanciaOriginal + 300;

        // 100 metros = 0.001 grados en latitud o longitud
        double delta = 0.00001 * distanciaConDelta;

        double latitudDesde = miUbicacion.latitude - delta;
        double latitudHasta = miUbicacion.latitude + delta;
        double longitudDesde = miUbicacion.longitude - delta;
        double longitudHasta = miUbicacion.longitude + delta;

        String requestURL = Uri.parse(this.getString(R.string.urlAPI) + "/students/" + username + "/mates/locations")
                .buildUpon()
                .appendQueryParameter("latitudeFrom", String.valueOf(latitudDesde))
                .appendQueryParameter("latitudeTo", String.valueOf(latitudHasta))
                .appendQueryParameter("longitudeFrom", String.valueOf(longitudDesde))
                .appendQueryParameter("longitudeTo", String.valueOf(longitudHasta))
                .build().toString();

        JsonArrayRequest studentReq = new JsonArrayRequest(requestURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        double[] lat = new double[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                String userName = obj.getString("userName");
                                String name = obj.getString("name");
                                String lastName = obj.getString("lastName");
                                String profilePicture = obj.getString("profilePicture");
                                String latitude = obj.getString("latitude");
                                String longitude = obj.getString("longitude");

                                double latitud = Double.parseDouble(latitude);
                                double longitud = Double.parseDouble(longitude);

                                float[] distance = new float[2];
                                Location.distanceBetween(latitud, longitud, miUbicacion.latitude, miUbicacion.longitude, distance);

                                if( distance[0] <= distanciaOriginal  ) {
                                    agregarCompaniero(profilePicture, latitude, longitude, userName, name, lastName, "false");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);
                return headers;
            }
        };

        VolleyController.getInstance().addToRequestQueue(studentReq);
    }

    public void configurarInfoMarker(){
        getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker args) {
                return null;
            }

            @Override
            public View getInfoContents(Marker args) {

                View markerInfo = getLayoutInflater().inflate(R.layout.mapa_info_marker, null);

                String infoAlumno = args.getSnippet();
                String[] datosAlumno = infoAlumno.split("-");

                String nombre = datosAlumno[0];
                String apellido = datosAlumno[1];

                TextView tvNombre = (TextView) markerInfo.findViewById(R.id.tvNombre);
                tvNombre.setText(nombre + " " + apellido);

                getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    public void onInfoWindowClick(Marker marker) {
                        visitarPerfil(marker);
                    }
                });

                return markerInfo;
            }
        });
    }

    private void visitarPerfil(Marker marker){

        SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);

        String infoAlumno = marker.getSnippet();
        String[] datosAlumno = infoAlumno.split("-");
        String usuario = datosAlumno[2];
        final String soyYo = datosAlumno[3];

        String requestURL = Uri.parse(this.getString(R.string.urlAPI) + "/students/" + usuario)
                .buildUpon()
                .build().toString();

        JsonObjectRequest studentReq = new JsonObjectRequest(requestURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!soyYo.toLowerCase().equals("true")) {
                                Intent i = new Intent(getApplicationContext(), PerfilTabsCompanero.class);
                                i.putExtra("name", response.getString("name"));
                                i.putExtra("lastName", response.getString("lastName"));
                                i.putExtra("userName", response.getString("userName"));
                                i.putExtra("comments", response.getString("comments"));
                                i.putExtra("isMyMate", true);

                                if(response.getString("isExchangeStudent").toLowerCase().equals(("true")))
                                    i.putExtra("isExchange", true);
                                else
                                    i.putExtra("isExchange", false);

                                startActivity(i);
                            } else {
                                Intent i = new Intent(getApplicationContext(), PerfilTabs.class);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);
                return headers;
            }
        };

        VolleyController.getInstance().addToRequestQueue(studentReq);
    }

    public void agregarCompaniero(final String profilePicture, final String latitude, final String longitude, final String userName, final String name, final String lastName, final String soyYo){

        ImageRequest ir = new ImageRequest(this.getString(R.string.urlAPI) + "/students/" + userName + "/picture", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                if(getFragmentManager() == null)
                    return;

                GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

                double latitud = Double.parseDouble(latitude);
                double longitud = Double.parseDouble(longitude);

                LatLng latLong = new LatLng(latitud, longitud);

                Bitmap imageProfile = response;

                Bitmap bhalfsize= Bitmap.createScaledBitmap(imageProfile, imageProfile.getWidth() / 3, imageProfile.getHeight() / 3, false);

                String infoAlumno = name + "-" + lastName + "-" + userName + "-" + soyYo;
                MarkerOptions options = new MarkerOptions()
                        .anchor(0.5f, 0.5f)
                        .snippet(infoAlumno)
                        .position(latLong)
                        .icon(BitmapDescriptorFactory.fromBitmap(bhalfsize));

                map.addMarker(options);
            }
        }, 0, 0, null, null);

        VolleyController.getInstance().addToRequestQueue(ir);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    public GoogleMap getMap(){
        return ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }
}