package com.fiubapp.fiubapp;

import android.app.Activity;
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
import com.fiubapp.fiubapp.dominio.Grupo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.groups.GruposTabs;

public class GroupsSearch extends Activity{

    private List<Grupo> groupList = new ArrayList<Grupo>();
    private ListView groups;
    private GrupoAdapter grupoAdapter;
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

        grupoAdapter = new GrupoAdapter(this, groupList);
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

                                groupList.add(grupo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (groupList.size()==0) {
                            Popup.showText(getBaseContext(), "No se encontraron grupos que coincidan con la busqueda.", Toast.LENGTH_LONG).show();
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

}
