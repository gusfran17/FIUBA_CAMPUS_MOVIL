package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

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
            agregarCompanero(convertView,position);
        }

        return convertView;
    }

    private void agregarCompanero(View convertView, int position) {

    }

    private void eliminarCompanero(final Alumno companero, View convertView, final int position) {

        final Button eliminar = (Button)convertView.findViewById(R.id.childButton);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", companero.getUsername());

                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                        buildNotificationsUrl(),
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Popup.showText(activity, "Anduvo joya", Toast.LENGTH_LONG);
                                alumnoItems.remove(position);
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Popup.showText(activity, "Anduvo mal", Toast.LENGTH_LONG);
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

    private String buildNotificationsUrl(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getURLAPI() + "/students/" + dataAccess.getUserName() + "/mates";
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }

}
