package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.fiubapp.fiubapp.dominio.MensajeMuro;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class MuroAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MensajeMuro> muroItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public MuroAdapter(Activity activity, List<MensajeMuro> muroItems) {
        this.activity = activity;
        this.muroItems = muroItems;
    }

    @Override
    public int getCount() {
        return muroItems.size();
    }

    @Override
    public Object getItem(int location) {
        return muroItems.get(location);
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
            grupoFilaView = inflater.inflate(R.layout.msg_muro, null);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView)grupoFilaView.findViewById(R.id.thumbnail);

        TextView nombre = (TextView) grupoFilaView.findViewById(R.id.nombre);
        TextView remitente = (TextView) grupoFilaView.findViewById(R.id.username);
        TextView fecha = (TextView) grupoFilaView.findViewById(R.id.fecha);
        EditText mensaje = (EditText)grupoFilaView.findViewById(R.id.message);
        Button botonEliminar = (Button)grupoFilaView.findViewById(R.id.msgButton);
        ImageView candado = (ImageView)grupoFilaView.findViewById(R.id.privado);

        final MensajeMuro msj = muroItems.get(position);

        thumbNail.setImageUrl(msj.getRemitente().getImgURL(),imageLoader);

        nombre.setText(msj.getRemitente().getNombreApellido());
        fecha.setText(msj.getFecha());
        mensaje.setText(msj.getMensaje());
        mensaje.setBackgroundColor(Color.TRANSPARENT);

        if (!msj.isMsjPrivado())
            candado.setVisibility(View.INVISIBLE);

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("¿Eliminar mensaje?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarMensaje(msj);
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

        return grupoFilaView;
    }

    private void eliminarMensaje(final MensajeMuro msj){
        String url = getURLAPI()+"/students/"+getUsername()+"/messages/"+msj.getId();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(activity, "El mensaje fue eliminado de tu muro", Toast.LENGTH_LONG).show();

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

    /*private void ingresarGrupo(final MensajeMuro grupo){
        String url = getURLAPI()+"/students/"+getUsername()+"/groups/"+grupo.getId();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(activity, "Te inscribiste al grupo "+grupo.getNombre(), Toast.LENGTH_LONG).show();
                        muroItems.remove(grupo);
                        grupo.setCantMiembros(grupo.getCantMiembros()+1);
                        grupo.setAmIaMember(true);
                        muroItems.add(grupo);

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

    class imageViewClickListener implements View.OnClickListener {
        int position;

        public imageViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {

            }
        }
    }*/

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
