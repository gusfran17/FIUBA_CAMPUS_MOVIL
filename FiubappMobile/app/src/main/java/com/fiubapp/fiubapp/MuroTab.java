package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
        adapter = new MuroAdapter(getActivity(), mensajes);

        listView.setAdapter(adapter);

        if (getArguments() != null) {

            if (getArguments().getBoolean("isMyMate")) {

                getMensajesMuro(getArguments().getString("userName"));

            }
            return view;
        }

        //Mensajes de muro
        Alumno remitente = new Alumno();
        remitente.setUsername("80013");
        remitente.setNombre("Agustin");
        remitente.setApellido("Sangregorio");
        remitente.setImgURL("http://image-load-balancer.worldsportshops.com/Datafeeds/Graphics/Products/ImageCache/600x600/92151~HAZARD~10.jpg");

        MensajeMuro msj = new MensajeMuro();
        msj.setNombre(remitente.getNombre());
        msj.setRemitente(remitente);
        msj.setFecha("23/05/2015 18:00:03");
        msj.setMsjPrivado(false);
        msj.setMensaje("And Mourinho has highlighted the importance of the duo, telling BBC's Football Focus: " +
                "\"I felt before the start of my first season I needed them.\"");

        mensajes.add(msj);
        mensajes.add(msj);
        mensajes.add(msj);
        mensajes.add(msj);
        mensajes.add(msj);

        adapter.notifyDataSetChanged();

        final EditText input = new EditText(this.context);
        botonEscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setView(input)
                        .setTitle("Nuevo mensaje")
                        .setPositiveButton("Escribir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //postearMensaje(msj);
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

    private void getMensajesMuro(String username){
        String url = getURLAPI()+"/students/"+username+"/messages/";

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Alumno remitente = new Alumno();
                                companero.setNombre(obj.getString("name"));
                                companero.setApellido(obj.getString("lastName"));
                                companero.setIntercambio(obj.getBoolean("isExchangeStudent"));
                                companero.setIsMyMate(obj.getBoolean("isMyMate"));
                                companero.setUsername(obj.getString("userName"));
                                companero.setImgURL(obj.getString("profilePicture"));

                                JSONArray JSONCareers = new JSONArray(obj.getString("careers"));
                                ArrayList<String> carreras = new ArrayList<>();

                                for(int j=0; j < JSONCareers.length(); j++){

                                    String carrera = JSONCareers.getString(j);

                                    carreras.add(carrera);
                                }
                                companero.setCarreras(carreras);

                                mensajes.add(companero);

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

    public static MuroTab newContact(Alumno companero) {

        MuroTab perfil = new MuroTab();

        Bundle args = new Bundle();
        args.putString("name",companero.getNombre());
        args.putString("lastName",companero.getApellido());
        args.putString("userName",companero.getUsername());
        args.putBoolean("isMyMate",companero.isMyMate());

        perfil.setArguments(args);

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