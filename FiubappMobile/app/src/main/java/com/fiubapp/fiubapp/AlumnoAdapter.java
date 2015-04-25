package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class AlumnoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Alumno> alumnoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public AlumnoAdapter(Activity activity, List<Alumno> alumnoItems) {
        this.activity = activity;
        this.alumnoItems = alumnoItems;
    }

    @Override
    public int getCount() {
        return alumnoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return alumnoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        /*if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);*/
        //ImageView imagen = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView carrera = (TextView) convertView.findViewById(R.id.carrera);

        final Button buttonAdd = (Button) convertView.findViewById(R.id.childButton);
        Alumno a = alumnoItems.get(position);

        nombre.setText(a.getNombre() +" "+ a.getApellido());
        username.setText(a.getUsername());

        if (!a.getCarreras().isEmpty())
            carrera.setText(a.getCarreras().get(0));
        else carrera.setText("");

        if (a.isMyMate()) {
            buttonAdd.setText("Eliminar");
            eliminarCompanero((Alumno)alumnoItems.get(position),convertView ,position);
        } else {
            buttonAdd.setText("Agregar");
            agregarCompanero((Alumno)alumnoItems.get(position),convertView,position);
        }

        return convertView;
    }

	private void agregarCompanero(final Alumno companero, View convertView, final int position) {

        final Button agregar = (Button)convertView.findViewById(R.id.childButton);
        final Map<String, String> params = new HashMap<String, String>();
        params.put("applicantUserName", getUsername());

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                        getURLAPI()+"/students/" + companero.getUsername() +"/applications",
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //alumnoItems.add(companero);
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String responseBody = null;

                                try {
                                    responseBody = new String( error.networkResponse.data, "utf-8" );
                                    //JSONObject jsonObject = new JSONObject( responseBody );
                                    Log.d("Error 415: ",responseBody);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
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
        });


    }

    private void eliminarCompanero(final Alumno companero, View convertView, final int position) {

        final Button eliminar = (Button)convertView.findViewById(R.id.childButton);
        final String urlAPI = activity.getResources().getString(R.string.urlAPI);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  urlAPI+"/students/"+getUsername()+"/mates/"+companero.getUsername();
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                       url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                alumnoItems.remove(position);
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //el response es null cuando borra al compañero,
                                //entonces se borra del listado
                                if (error.networkResponse == null){
                                    alumnoItems.remove(position);
                                    notifyDataSetChanged();
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
        });

    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getURLAPI();
    }

}
