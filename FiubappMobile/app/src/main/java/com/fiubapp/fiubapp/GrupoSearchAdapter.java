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

public class GrupoSearchAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Grupo> grupoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public GrupoSearchAdapter(Activity activity, List<Grupo> grupoItems) {
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
            //alertaAbandonarGrupo(grupoSeleccionado,grupoFilaView);
            button.setVisibility(View.INVISIBLE);
        }else{
            button.setText("Ingresar");
            alertaIngresarGrupo(grupoSeleccionado, grupoFilaView);
            button.setVisibility(View.VISIBLE);
        }

        return grupoFilaView;
    }

    public void alertaIngresarGrupo(final Grupo grupo, View view){
        final Button buttonIngresar = (Button)view.findViewById(R.id.childButton);

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        new AlertDialog.Builder(activity)
                                        .setTitle(R.string.ingresar_a_grupo)
                                        .setMessage(grupo.getNombre())
                                        .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                ingresarGrupo(grupo);
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
            });
    }

    private void ingresarGrupo(final Grupo grupo){
                String url = getURLAPI()+"/students/"+getUsername()+"/groups/"+grupo.getId();

                        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                                url,
                                new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                                Popup.showText(activity, "Te inscribiste al grupo "+grupo.getNombre(), Toast.LENGTH_LONG).show();
                                                grupoItems.remove(grupo);
                                                grupo.setCantMiembros(grupo.getCantMiembros() + 1);
                                                grupo.setAmIaMember(true);
                                                grupoItems.add(grupo);

                                                        notifyDataSetChanged();
                                            }
                                    },
                                new Response.ErrorListener(){
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                                Popup.showText(activity, "Ha ocurrido un error. " +
                                                                "Probá nuevamente en unos minutos", Toast.LENGTH_LONG).show();
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
