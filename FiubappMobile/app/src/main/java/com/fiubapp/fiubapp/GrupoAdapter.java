package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.fiubapp.fiubapp.dominio.Grupo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class GrupoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Grupo> grupoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public GrupoAdapter(Activity activity, List<Grupo> grupoItems) {
        this.activity = activity;
        this.grupoItems = grupoItems;
    }

    @Override
    public int getCount() {
        return grupoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return grupoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View grupoFilaView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (grupoFilaView == null)
            grupoFilaView = inflater.inflate(R.layout.grupo_fila, null);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView)grupoFilaView.findViewById(R.id.thumbnail);

        TextView nombre = (TextView) grupoFilaView.findViewById(R.id.nombre_grupo);
        TextView cantidadGrupo = (TextView) grupoFilaView.findViewById(R.id.cantidad_grupo);
        Button button = (Button)grupoFilaView.findViewById(R.id.childButton);

        final Grupo grupoSeleccionado = grupoItems.get(position);
        nombre.setText(grupoSeleccionado.getNombre());

        int time = (int)(System.currentTimeMillis());
        thumbNail.setImageUrl(grupoSeleccionado.getImgURL() + "?timestamp=" + time, imageLoader);

        thumbNail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                String url = grupoSeleccionado.getImgURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
        }
        });

        int cantMiembros = grupoSeleccionado.getCantMiembros();
        String cantidad = "";
        if (cantMiembros == 1)
            cantidad = cantMiembros + " miembro";

        else cantidad = cantMiembros + " miembros";

        cantidadGrupo.setText(cantidad);

        if (grupoSeleccionado.getAmIaMember()){
            //button.setText("Abandonar");
            button.setVisibility(View.INVISIBLE);
			//alertaAbandonarGrupo(grupoSeleccionado,grupoFilaView);
        }else{
            button.setVisibility(View.INVISIBLE);
            //button.setText("Ingresar");
            //alertaIngresarGrupo(grupoSeleccionado,grupoFilaView);
        }

        return grupoFilaView;
    }

    public void getGrupos(){
        grupoItems.clear();

        String URL = getURLAPI() + "/students/" + getUsername() + "/groups";
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                URL,
                //new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject grupoJSON = response.getJSONObject(i);

                                String id = grupoJSON.getString("id");
                                String nombre = grupoJSON.getString("name");

                                Grupo grupo = new Grupo();

                                grupo.setId(id);
                                grupo.setNombre(nombre);
                                grupo.setCantMiembros(grupoJSON.getInt("amountOfMembers"));
                                grupo.setAmIaMember(grupoJSON.getBoolean("amIAMember"));
                                grupo.setImgURL(grupoJSON.getString("groupPicture"));

                                JSONObject jsonOwner = grupoJSON.getJSONObject("owner");
                                Alumno owner = new Alumno();

                                owner.setNombre(jsonOwner.getString("name"));
                                owner.setApellido(jsonOwner.getString("lastName"));
                                owner.setIntercambio(jsonOwner.getBoolean("isExchangeStudent"));
                                owner.setIsMyMate(jsonOwner.getBoolean("isMyMate"));
                                owner.setUsername(jsonOwner.getString("userName"));
                                owner.setImgURL(jsonOwner.getString("profilePicture"));

                                grupo.setOwner(owner);

                                grupoItems.add(grupo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        notifyDataSetChanged();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(GrupoAdapter.class.getSimpleName(), "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Authorization", getToken());

                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);
    }

    class imageViewClickListener implements View.OnClickListener {
        int position;

        public imageViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {

            }
        }
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
