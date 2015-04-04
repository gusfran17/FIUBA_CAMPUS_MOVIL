package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register2 extends Activity{

    private EmailValidator emailValidator;
    private static final String TAG = Register2.class.getSimpleName();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final boolean intercambio = intent.getBooleanExtra("isExchange",false);
        final Spinner spinner_carrera = (Spinner)findViewById(R.id.reg_carrera);
        final EditText edit_nombre = (EditText)findViewById(R.id.reg_nombre);
        final Button button = (Button) findViewById(R.id.btnRegister2);
        final EditText edit_apellido = (EditText)findViewById(R.id.reg_apellido);
        final EditText edit_email = (EditText)findViewById(R.id.reg_email);

        final ArrayList<String> careers = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(Register2.this,R.layout.simple_spinner_item,careers);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner_carrera.setAdapter(adapter);

        // Creating volley request obj
        JsonArrayRequest careerReq = new JsonArrayRequest("http://10.0.2.2:8080/fiubappREST/api/careers/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                careers.add(obj.getString("name"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.d(TAG, Integer.toString(error.networkResponse.statusCode));
                }

            }
        });

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(careerReq);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String nombre = edit_nombre.getText().toString();
                final String apellido = edit_apellido.getText().toString();
                final String email = edit_email.getText().toString();
                final int carrera = (int)spinner_carrera.getSelectedItemId();

                //si ninguno de los campos estan vacios
                if (!nombre.equals("") && !apellido.equals("") && !email.equals("")){
                    //si el mail es valido llamo al REST
                    emailValidator = new EmailValidator();
                    if (emailValidator.validate(email)) {

                        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST,
                                "http://10.0.2.2:8080/fiubappREST/api/students",
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {}
                                },
                                new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();

                                //se cargan los datos para generar el request POST
                                params.put("userName", username);
                                params.put("password", password);
                                params.put("name",nombre);
                                params.put("lastName", apellido);
                                params.put("email", email);
                                params.put("isExchange",Boolean.toString(intercambio));
                                params.put("carrera", Integer.toString(carrera));

                                return params;
                            }

                            @Override
                            public String getBodyContentType(){
                                return "application/json; charset=utf-8";
                            }
                        };

                        //llamada al POST
                        //VolleyController.getInstance().addToRequestQueue(jsonReq);
                        Toast.makeText(Register2.this, "La cuenta ha sido creada, " +
                                "se le enviará un email de confirmación", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);

                    //aviso de mail no valido
                    }else{
                        Toast.makeText(Register2.this, "El email no es válido", Toast.LENGTH_LONG).show();
                    }
                //aviso de campo(s) vacio(s)
                }else{
                    Toast.makeText(Register2.this, "Nombre, apellido y/o email vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
