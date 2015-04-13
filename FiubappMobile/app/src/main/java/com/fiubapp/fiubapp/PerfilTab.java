package com.fiubapp.fiubapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PerfilTab extends Fragment {

    private static final String TAG = FragmentTab2.class.getSimpleName();
    private String urlAPI="";
    private TextView perfil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isAdded()){
            urlAPI = getResources().getString(R.string.urlAPI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perfiltab, container, false);
        perfil = (TextView)view.findViewById(R.id.verperfil);

        final TextView textName = (TextView)view.findViewById(R.id.nombre);

        final SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String username = settings.getString("username", null);

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                urlAPI+"/students/"+username,
                //new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        try {
                            textName.setText(response.getString("name")+" "+response.getString("lastName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            textName.setText(username);
                        }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();

                        String token = settings.getString("token", null);
                        headers.put("Authorization", token);

                        return headers;

                    }
        };
        VolleyController.getInstance().addToRequestQueue(jsonReq);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Perfil.class);
                startActivity(i);
            }
        });
        return view;
    }

}
