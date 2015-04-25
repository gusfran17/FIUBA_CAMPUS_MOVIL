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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register2 extends Activity{

    private EmailValidator emailValidator;
    private static final String TAG = Register2.class.getSimpleName();
    private SpinnerObject spinnerObject;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final boolean intercambio = intent.getBooleanExtra("isExchange",false);
        final Spinner spinner_carrera = (Spinner)findViewById(R.id.reg_carrera);
        final TextView text_carrera = (TextView) findViewById(R.id.text_carrera);
        final EditText edit_nombre = (EditText)findViewById(R.id.reg_nombre);
        final Button button = (Button) findViewById(R.id.btnRegister2);
        final EditText edit_apellido = (EditText)findViewById(R.id.reg_apellido);
        final EditText edit_email = (EditText)findViewById(R.id.reg_email);

        final ArrayList<SpinnerObject> careers = new ArrayList<SpinnerObject>();
        final ArrayAdapter adapter = new ArrayAdapter<SpinnerObject>(Register2.this,R.layout.simple_spinner_item,careers);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner_carrera.setAdapter(adapter);
        if (intercambio){
            spinner_carrera.setVisibility(View.INVISIBLE);
            text_carrera.setVisibility(View.INVISIBLE);
        }else {
            text_carrera.setVisibility(View.VISIBLE);
            spinner_carrera.setVisibility(View.VISIBLE);
        }
        spinnerObject = null;
        final String urlAPI = this.getString(R.string.urlAPI);

          JsonArrayRequest careerReq = new JsonArrayRequest(urlAPI+"/careers/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        spinnerObject = new SpinnerObject(0,"Seleccione una carrera");
                        careers.add(spinnerObject);
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                spinnerObject = new SpinnerObject(obj.getInt("code"),obj.getString("name"));
                                careers.add(spinnerObject);

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

        //se agrega el request para obtener las carreras
        VolleyController.getInstance().addToRequestQueue(careerReq);

        //cuando se clickea en el boton de Registrarse
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String nombre = edit_nombre.getText().toString();
                final String apellido = edit_apellido.getText().toString();
                final String email = edit_email.getText().toString();
                SpinnerObject spnItem = (SpinnerObject)spinner_carrera.getSelectedItem();
                final int carrera = spnItem.getID();
                //si ninguno de los campos estan vacios
                if (!nombre.equals("") && !apellido.equals("") && !email.equals("")){
                    //si el mail es valido llamo al REST
                    emailValidator = new EmailValidator();
                    if (emailValidator.validate(email)) {

                        Map<String, String> params = new HashMap<String, String>();

                        //cargo los key,values en 'params', para formar el JSON

                        params.put("password", password);
                        params.put("name", nombre);
                        params.put("lastName", apellido);
                        params.put("email", email);
                        params.put("isExchangeStudent", Boolean.toString(intercambio));

                        //guarda la carrera solo si no selecciono "Seleccione una carrera"
                        if (carrera > 1)
                            params.put("careerCode", Integer.toString(carrera));
                        else params.put("careerCode",null);

                        if (intercambio) {
                            //se cargan los datos para generar el request POST
                            params.put("fileNumber", null);
                            params.put("passportNumber", username);
                        }else{
                            params.put("fileNumber", username);
                            params.put("passportNumber", null);
                        }

                        JsonObjectRequest jsonReq = new JsonObjectRequest(
                                urlAPI+"/students",
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Error: " + response.toString());

                                        Popup.showText(Register2.this, "La cuenta ha sido creada, " +
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
                                            //parseo la respuesta del server para obtener JSON
                                            String body = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                                            JSONObject JSONBody = new JSONObject(body);

                                            //obtiene el mensaje de respuesta del server
                                            String mensaje = JSONBody.getString("message");

                                            //muestra el mensaje
                                            Popup.showText(Register2.this, mensaje, Toast.LENGTH_LONG).show();

                                            //redirige a la pantalla inicial de registro
                                            Intent i = new Intent(getBaseContext(), Register1.class);
                                            startActivity(i);

                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (NullPointerException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );

                        //llamada al POST
                        VolleyController.getInstance().addToRequestQueue(jsonReq);

                    //aviso de mail no valido
                    }else{
                        Popup.showText(Register2.this, "El email no es válido", Toast.LENGTH_LONG).show();
                    }
                //aviso de campo(s) vacio(s)
                }else{
                    Popup.showText(Register2.this, "Nombre, apellido y/o email vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
