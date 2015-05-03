package com.fiubapp.fiubapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
        zoom = CameraUpdateFactory.zoomTo(15);

        getMap().moveCamera(center);
        getMap().animateCamera(zoom);
    }

    private void obtenerMisDatos(){

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
                            Alumno student = new Alumno();
                            student.setNombre(response.getString("name"));
                            student.setApellido(response.getString("lastName"));
                            student.setIntercambio(response.getBoolean("isExchangeStudent"));
                            student.setUsername(response.getString("userName"));
                            student.setComentario(response.getString("comments"));

                            agregarCompaniero(R.drawable.yo, miUbicacion, student, "true");

                            getMap().getUiSettings().setZoomControlsEnabled(true);

                            Circle circle = getMap().addCircle(new CircleOptions()
                                    .center(miUbicacion)
                                    .radius(200)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(0x404099ff)
                                    .strokeWidth(3));

                            dibujarCompanieros();

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

        String name = "";
        String lastname = "";

        String requestURL = Uri.parse(this.getString(R.string.urlAPI) + "/students")
                .buildUpon()
                .appendQueryParameter("name", name)
                .appendQueryParameter("lastName", lastname)
                .build().toString();

        JsonArrayRequest studentReq = new JsonArrayRequest(requestURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        double[] lat = new double[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            if(i == 0)
                                lat[0] = -34.743110;
                            if(i == 1)
                                lat[1] = -34.741805;
                            if(i == 2)
                                lat[2] = -34.742415;
                            if(i == 3)
                                lat[3] = -34.744073;
                        }

                        double[] lng = new double[response.length()];
                        for (int i = 0; i < response.length(); i++) {
                            if(i == 0)
                                lng[0] = -58.351360;
                            if(i == 1)
                                lng[1] = -58.351317;
                            if(i == 2)
                                lng[2] = -58.348401;
                            if(i == 3)
                                lng[3] = -58.349635;
                        }
                        int[] img = new int[response.length()];
                        for (int i = 0; i < response.length(); i++) {
                            if(i == 0)
                                img[0] = R.drawable.jaqui;
                            if(i == 1)
                                img[1] = R.drawable.agus;
                            if(i == 2)
                                img[2] = R.drawable.vero;
                            if(i == 3)
                                img[3] = R.drawable.mari;
                        }

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

                                agregarCompaniero(img[i], new LatLng(lat[i], lng[i]), student, "false");

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

                        String infoAlumno = marker.getSnippet();
                        String[] datosAlumno = infoAlumno.split("-");

                        String nombre = datosAlumno[0];
                        String apellido = datosAlumno[1];
                        String usuario = datosAlumno[2];
                        String esIntercambio = datosAlumno[3];
                        String esAmigo = datosAlumno[4];
                        String soyYo = datosAlumno[5];

                        boolean intercambio = false;
                        if (esIntercambio.toLowerCase().equals("true"))
                            intercambio = true;

                        boolean amigo = false;
                        if (esAmigo.toLowerCase().equals("true"))
                            amigo = true;

                        String comentarios = "";
                        if (datosAlumno.length > 6)
                            comentarios = datosAlumno[6];

                        if (!soyYo.toLowerCase().equals("true")) {
                            Intent i = new Intent(getApplicationContext(), PerfilTabsCompanero.class);
                            i.putExtra("name", nombre);
                            i.putExtra("lastName", apellido);
                            i.putExtra("userName", usuario);
                            i.putExtra("comments", comentarios);
                            i.putExtra("isExchange", intercambio);
                            i.putExtra("isMyMate", amigo);

                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), PerfilTabs.class);
                            startActivity(i);
                        }
                    }
                });

                return markerInfo;
            }
        });
    }

    public void agregarCompaniero(int idImage, LatLng ubicacion, Alumno alumno, String soyYo){
        BitmapDrawable d=(BitmapDrawable) getResources().getDrawable(idImage);
        Bitmap b=d.getBitmap();

        Bitmap bhalfsize= Bitmap.createScaledBitmap(b, b.getWidth() / 3, b.getHeight() / 3, false);

        String comentario = "";
        if(!alumno.getComentario().equals("null"))
            comentario = alumno.getComentario();

        String infoAlumno = alumno.getNombre() + "-" + alumno.getApellido() + "-" + alumno.getUsername() + "-" + alumno.isIntercambio() + "-"  + alumno.isMyMate() + "-" + soyYo + "-" + comentario;
        MarkerOptions options = new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .snippet(infoAlumno)
                .position(ubicacion)
                .icon(BitmapDescriptorFactory.fromBitmap(bhalfsize));

        this.getMap().addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    public GoogleMap getMap(){
        return ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }
}