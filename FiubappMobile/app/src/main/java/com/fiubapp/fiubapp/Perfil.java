package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Perfil extends Activity{

    //private String urlAPI = getResources().getString(R.string.urlAPI);
    private String username = "";
    private static final String TAG = Perfil.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        final EditText edit_nac = (EditText)findViewById(R.id.edit_nac);
        final EditText edit_ciudad = (EditText)findViewById(R.id.edit_ciudad);
        final EditText edit_telefono = (EditText)findViewById(R.id.edit_tel);
        final EditText edit_email = (EditText)findViewById(R.id.edit_email);

        edit_email.setEnabled(false);


        String urlAPI = getResources().getString(R.string.urlAPI);
        SharedPreferences settings = this.getSharedPreferences(
                getResources().getString(R.string.prefs_name), 0);
        username = settings.getString("username",null);



        //obtener datos
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                urlAPI+"/students/"+username,
                //new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // VolleyLog.d(TAG, "Response: " + response.toString());

                        try {
                            String name = response.getString("name");
                            String lastName = response.getString("lastName");

                            String email = response.getString("email");
                            String comments = response.getString("comments");
                            String gender = response.getString("gender");
                            String currentCity = response.getString("currentCity");
                            String nationality = response.getString("nationality");
                            String phoneNumber = response.getString("phoneNumber");

                            if (currentCity != "null"){
                                edit_ciudad.setText(currentCity);
                            }
                            if (phoneNumber != "null") {
                                edit_telefono.setText(phoneNumber);
                            }
                            if (email != "null") {
                                edit_email.setText(email);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(
                        getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);

                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);


        /*button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String nombre = edit_nombre.getText().toString();
                final String apellido = edit_apellido.getText().toString();
                final String email = edit_email.getText().toString();
                final int carrera = (int)spinner_carrera.getSelectedItemId() + 1;

                //si ninguno de los campos estan vacios
                if (!nombre.equals("") && !apellido.equals("") && !email.equals("")){
                    //si el mail es valido llamo al REST
                    emailValidator = new EmailValidator();
                    if (emailValidator.validate(email)) {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("password", password);
                        params.put("name", nombre);
                        params.put("lastName", apellido);
                        params.put("email", email);
                        params.put("isExchangeStudent", Boolean.toString(intercambio));
                        params.put("careerCode", Integer.toString(carrera));

                        //cargo los key,values en 'params', para formar el JSON
                        if (intercambio) {
                            //se cargan los datos para generar el request POST
                            params.put("fileNumber", null);
                            params.put("passportNumber", username);
                        }else{
                            params.put("fileNumber", username);
                            params.put("passportNumber", null);
                        }

                        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.PUT,
                                urlAPI+"/students",
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Error: " + response.toString());

                                        Toast.makeText(Perfil.this, "La cuenta ha sido creada, " +
                                                "se le enviará un email de confirmación", Toast.LENGTH_LONG).show();

                                        //se redirige al login
                                        Intent i = new Intent(getBaseContext(), Login.class);
                                        startActivity(i);
                                    }
                                },
                                new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                                        try {
                                            //muestra el mensaje
                                            Toast.makeText(Perfil.this, mensaje, Toast.LENGTH_LONG).show();

                                            //redirige a la pantalla inicial de registro
                                            //Intent i = new Intent(getBaseContext(), Register1.class);
                                            //startActivity(i);

                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );

                        //llamada al POST
                        VolleyController.getInstance().addToRequestQueue(jsonReq);

                        //aviso de mail no valido
                    }else{
                        Toast.makeText(Perfil.this, "El email no es válido", Toast.LENGTH_LONG).show();
                    }
                    //aviso de campo(s) vacio(s)
                }else{
                    Toast.makeText(Perfil.this, "Nombre, apellido y/o email vacíos", Toast.LENGTH_LONG).show();
                }

            }
        }*/
    }
}
