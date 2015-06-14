package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.dominio.MensajeMuro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class MuroTab extends Fragment {

    private static final String TAG = MuroTab.class.getSimpleName();

    private String urlAPI="";
    private List<MensajeMuro> mensajes = new ArrayList<MensajeMuro>();
    private ListView listView;
    private MuroAdapter adapter;
    private Context context;
    private static boolean isOwnWall = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.muro_tab, container, false);
        Button botonEscribir = (Button)view.findViewById(R.id.escribirButton);
        listView = (ListView)view.findViewById(R.id.muroList);

        if (!isOwnWall){
            getWallConfigurationForMate(this.getArguments().getString("userName"));
        }


        if (getArguments() != null) {

            if (getArguments().getBoolean("isMyMate")) {

                final String usernameCompanero = getArguments().getString("userName");
                getMensajesMuro(usernameCompanero);

                adapter = new MuroAdapter(getActivity(), mensajes, false);
                listView.setAdapter(adapter);

                botonEscribir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final EditText input = new EditText(context);
                        final CheckBox checkBox = new CheckBox(context);
                        checkBox.setTextColor(Color.WHITE);
                        checkBox.setText("Mensaje Privado");
                        final LinearLayout layout = new LinearLayout(context);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.addView(input);
                        layout.addView(checkBox);

                        new AlertDialog.Builder(getActivity())
                                .setView(layout)
                                .setTitle("Nuevo mensaje")
                                .setPositiveButton("Escribir", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String mensaje = input.getText().toString();
                                        if (mensaje.equals(""))
                                            Popup.showText(context,"El mensaje no puede estar vacío",Toast.LENGTH_LONG).show();
                                        else if (mensaje.length()>140)
                                            Popup.showText(context,"El mensaje no puede superar los 140 caracteres",Toast.LENGTH_LONG).show();
                                        else
                                            postearMensaje(usernameCompanero,mensaje,checkBox.isChecked());
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(0)
                                .show();
                    }
                });

            }
            return view;
        }

        adapter = new MuroAdapter(getActivity(), mensajes, true);
        listView.setAdapter(adapter);
        getMensajesMuro(getUsername());

        botonEscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(context);

                new AlertDialog.Builder(getActivity())
                        .setView(input)
                        .setTitle("Nuevo mensaje")
                        .setPositiveButton("Escribir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String mensaje = input.getText().toString();
                                if (mensaje.equals(""))
                                    Popup.showText(context,"El mensaje no puede estar vacío",Toast.LENGTH_LONG).show();
                                else if (mensaje.length()>140)
                                    Popup.showText(context,"El mensaje no puede superar los 140 caracteres",Toast.LENGTH_LONG).show();
                                else
                                    postearMensaje(getUsername(), mensaje, false);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(0)
                        .show();
            }
        });


        return view;
    }

    private void postearMensaje(String username, String message, boolean isPrivate){

        final Map<String, String> params = new HashMap<String, String>();
        params.put("message", message);
        params.put("isPrivate",Boolean.toString(isPrivate));
        params.put("creatorUserName",getUsername());

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                getURLAPI()+"/students/" + username +"/wall/messages",
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Alumno creator = new Alumno();
                        MensajeMuro mensaje = new MensajeMuro();

                        try {

                            mensaje.setFecha(response.getString("creationDate"));
                            mensaje.setMensaje(response.getString("message"));
                            mensaje.setId(response.getString("id"));
                            mensaje.setMsjPrivado(response.getBoolean("isPrivate"));

                            JSONObject JSONCreator = new JSONObject(response.getString("creator"));

                            creator.setUsername(getUsername());
                            creator.setNombre(JSONCreator.getString("name"));
                            creator.setApellido(JSONCreator.getString("lastName"));
                            creator.setIntercambio(JSONCreator.getBoolean("isExchangeStudent"));
                            creator.setImgURL(JSONCreator.getString("profilePicture"));
                            creator.setIsMyMate(JSONCreator.getBoolean("isMyMate"));

                            mensaje.setCreator(creator);

                            mensajes.add(0,mensaje);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String responseBody = null;

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

    private void getMensajesMuro(String username){

        this.mensajes.clear();

        String url = getURLAPI()+"/students/"+username+"/wall/messages/";

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                MensajeMuro mensaje = new MensajeMuro();
                                Alumno creator = new Alumno();

                                mensaje.setMensaje(obj.getString("message"));
                                mensaje.setMsjPrivado(obj.getBoolean("isPrivate"));
                                mensaje.setFecha(obj.getString("creationDate"));
                                mensaje.setId(obj.getString("id"));

                                JSONObject JSONCreator = new JSONObject(obj.getString("creator"));

                                for (int k=0; k < JSONCreator.length(); k++){
                                    creator.setNombre(JSONCreator.getString("name"));
                                    creator.setApellido(JSONCreator.getString("lastName"));
                                    creator.setIntercambio(JSONCreator.getBoolean("isExchangeStudent"));
                                    creator.setIsMyMate(JSONCreator.getBoolean("isMyMate"));
                                    creator.setUsername(JSONCreator.getString("userName"));
                                    creator.setImgURL(JSONCreator.getString("profilePicture"));

                                    JSONArray JSONCareers = new JSONArray(JSONCreator.getString("careers"));
                                    ArrayList<String> carreras = new ArrayList<>();

                                    for(int j=0; j < JSONCareers.length(); j++){

                                        String carrera = JSONCareers.getString(j);

                                        carreras.add(carrera);
                                    }
                                    creator.setCarreras(carreras);
                                }

                                mensaje.setCreator(creator);
                                mensajes.add(mensaje);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Popup.showText(context, "Ha ocurrido un error. " +
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

    private void getWallConfigurationForMate(String userName) {
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        String url = this.getString(R.string.urlAPI) + "/students/" + userName + "/configurations/wall";

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String isEnabled = response.getString("isEnabled");
                            if (isEnabled.equals(String.valueOf(false))){
                                disableWall();
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
                headers.put("Authorization", getToken());
                return headers;
            }
        };
        queue.add(jsObjRequest);
    }

    private void disableWall() {

        Button botonEscribir = (Button) this.getActivity().findViewById(R.id.escribirButton);
        botonEscribir.setVisibility(View.GONE);

    }


    public static MuroTab newContact(Alumno companero) {

        MuroTab perfil = new MuroTab();

        Bundle args = new Bundle();
        args.putString("name",companero.getNombre());
        args.putString("lastName",companero.getApellido());
        args.putString("userName",companero.getUsername());
        args.putBoolean("isMyMate",companero.isMyMate());

        perfil.setArguments(args);

        isOwnWall = false;

        return perfil;

    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getURLAPI();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
    }
}