package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fiubapp.fiubapp.dominio.Grupo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.groups.GruposTabs;
import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class GroupsSearch extends Activity{

    private List<Grupo> groupList = new ArrayList<Grupo>();
    private ListView groups;
    private GrupoSearchAdapter grupoAdapter;
    private static final String TAG = GroupsSearch.class.getSimpleName();
    private String urlAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.groups_search);

        urlAPI = getResources().getString(R.string.urlAPI);

        ImageView imageBack = (ImageView)findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnSearch = (Button) findViewById(R.id.button_search_groups);
        btnSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                fillGroupsList();
            }
        });

        groups = (ListView)findViewById(R.id.lst_regular_search_groups);

        groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo grupo = groupList.get(position);
                Intent i = new Intent(getApplicationContext(), GruposTabs.class);
                i.putExtra("idGrupo", Integer.parseInt(grupo.getId()));
                i.putExtra("nombreGrupo", grupo.getNombre());
                i.putExtra("esMiembro", grupo.getAmIaMember());
                startActivity(i);
            }
        });

        groups.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Grupo grupo = (Grupo)adapterView.getItemAtPosition(i);

                if (grupo.getAmIaMember()){
                    new AlertDialog.Builder(GroupsSearch.this)
                            .setTitle("¿Abandonar grupo?")
                            .setMessage(grupo.getNombre())
                            .setPositiveButton("Abandonar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    abandonarGrupo(grupo);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }else{
                    new AlertDialog.Builder(GroupsSearch.this)
                            .setTitle("Ingresar al grupo?")
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
                return true;
            }
        });

        grupoAdapter = new GrupoSearchAdapter(this, groupList);
        groups.setAdapter(grupoAdapter);
    }

    public void fillGroupsList(){

        EditText edit_group_name = (EditText)findViewById(R.id.edt_search_groups);
        String group_name = edit_group_name.getText().toString();

        groupList.clear();

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                urlAPI + "/groups"+"?name="+group_name,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject grupoJSON = response.getJSONObject(i);

                                Grupo grupo = new Grupo();

                                grupo.setId(grupoJSON.getString("id"));
                                grupo.setNombre(grupoJSON.getString("name"));
                                grupo.setCantMiembros(grupoJSON.getInt("amountOfMembers"));
                                grupo.setAmIaMember(grupoJSON.getBoolean("amIAMember"));
                                grupo.setImgURL(grupoJSON.getString("groupPicture"));

                                groupList.add(grupo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (groupList.size()==0) {
                            Popup.showText(GroupsSearch.this, "No se encontraron grupos que coincidan con la busqueda.", Toast.LENGTH_LONG).show();
                        }
                        grupoAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(
                        getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token", null);
                headers.put("Authorization", token);

                return headers;
            }

        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);

    }

    private void ingresarGrupo(final Grupo grupo){
        String url = getURLAPI()+"/students/"+getUsername()+"/groups/"+grupo.getId();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(GroupsSearch.this, "Te inscribiste al grupo "+grupo.getNombre(), Toast.LENGTH_LONG).show();
                        groupList.remove(grupo);
                        grupo.setCantMiembros(grupo.getCantMiembros()+1);
                        grupo.setAmIaMember(true);
                        groupList.add(grupo);

                        grupoAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Popup.showText(GroupsSearch.this, "Ha ocurrido un error. " +
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

    private void abandonarGrupo(final Grupo grupo){
        String url = getURLAPI()+"/students/"+getUsername()+"/groups/"+grupo.getId();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(GroupsSearch.this, "Abandonaste el grupo "+grupo.getNombre(), Toast.LENGTH_LONG).show();
                        fillGroupsList();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse == null){

                            Popup.showText(GroupsSearch.this, "Abandonaste el grupo "+grupo.getNombre(), Toast.LENGTH_LONG).show();
                            fillGroupsList();

                        }else{
                            Popup.showText(GroupsSearch.this, "Ha ocurrido un error. " +
                                    "Probá nuevamente en unos minutos", Toast.LENGTH_LONG).show();
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

    private String getToken(){
        DataAccess dataAccess = new DataAccess(this);
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(this);
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(this);
        return dataAccess.getURLAPI();
    }

}
