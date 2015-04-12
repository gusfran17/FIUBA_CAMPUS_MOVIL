package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Perfil extends Activity{

    private String urlAPI = "";
    private String username = "";
    private static final String TAG = Perfil.class.getSimpleName();
    private EmailValidator emailValidator;
    private String fecha;

    public void Perfil(String username){
        this.username = username;
    }

    // Process to get Current Date
    final Calendar c = Calendar.getInstance();
    private int mYear = c.get(Calendar.YEAR);
    private int mMonth = c.get(Calendar.MONTH);
    private int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        final TextView header_name = (TextView)findViewById(R.id.header_name);
        final TextView header_lastname = (TextView)findViewById(R.id.header_lastname);
        final TextView profile_name = (TextView)findViewById(R.id.profile_name);

        final EditText edit_comments = (EditText)findViewById(R.id.edit_comentarios);
        final EditText edit_email = (EditText)findViewById(R.id.edit_email);
        final EditText edit_telefono = (EditText)findViewById(R.id.edit_tel);
        final EditText edit_ciudad = (EditText)findViewById(R.id.edit_ciudad);
        final EditText edit_nacionalidad = (EditText)findViewById(R.id.edit_nacionalidad);
        final EditText edit_sexo = (EditText)findViewById(R.id.edit_sex);
        final EditText edit_fecha = (EditText)findViewById(R.id.edit_fecha);
        final ImageView edit_button = (ImageView)findViewById(R.id.editButton);

        edit_email.setEnabled(false);
        edit_telefono.setEnabled(false);
        edit_sexo.setEnabled(false);
        edit_ciudad.setEnabled(false);
        edit_nacionalidad.setEnabled(false);
        edit_fecha.setEnabled(false);

        urlAPI = getResources().getString(R.string.urlAPI);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_email.isEnabled()){

                    //obtengo todos los datos actuales
                    String name = header_name.getText().toString();
                    String lastName = header_lastname.getText().toString();
                    String email = edit_email.getText().toString();
                    String comments = edit_comments.getText().toString();
                    //String gender = edit_sexo.getText().toString();
                    String currentCity = edit_ciudad.getText().toString();
                    String nationality = edit_nacionalidad.getText().toString();
                    String phoneNumber = edit_telefono.getText().toString();

                    edit_fecha.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Launch Date Picker Dialog
                            DatePickerDialog dpd = new DatePickerDialog(Perfil.this,
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            // Display Selected date in textbox
                                           fecha = dayOfMonth + "/"
                                                   + (monthOfYear + 1) + "/" + year;
                                            edit_fecha.setText(fecha);

                                        }
                                    }, mYear, mMonth, mDay);
                            dpd.show();
                        }
                    });

                    //si ninguno de los campos estan vacios
                    if (!name.equals("") && !lastName.equals("") && !email.equals("")){
                        //si el mail es valido llamo al REST
                        emailValidator = new EmailValidator();
                        if (emailValidator.validate(email)) {

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("name", name);
                            params.put("lastName", lastName);
                            params.put("email", email);
                            params.put("dateOfBirth",fecha);
                            params.put("phoneNumber",phoneNumber);
                            params.put("currentCity",currentCity);
                            params.put("nationality",nationality);
                            params.put("comments",comments);

                            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.PUT,
                                    urlAPI+"/students/"+username,
                                    new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            VolleyLog.d(TAG, "Error: " + response.toString());

                                            edit_ciudad.setEnabled(false);
                                            edit_email.setEnabled(false);
                                            edit_nacionalidad.setEnabled(false);
                                            edit_telefono.setEnabled(false);
                                            //Toast.makeText(Perfil.this, "OK", Toast.LENGTH_LONG).show();

                                        }
                                    },
                                    new Response.ErrorListener(){
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            //Toast.makeText(Perfil.this, "ERROR", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ){
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

                edit_email.setEnabled(!edit_email.isEnabled());
                edit_telefono.setEnabled(!edit_telefono.isEnabled());
                edit_ciudad.setEnabled(!edit_ciudad.isEnabled());
                edit_nacionalidad.setEnabled(!edit_nacionalidad.isEnabled());

            }
        });

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
                       VolleyLog.d(TAG, "Response: " + response.toString());

                        try {
                            String name = response.getString("name");
                            String lastName = response.getString("lastName");

                            header_name.setText(name);
                            header_lastname.setText(lastName);
                            profile_name.setText(name);

                            String email = response.getString("email");
                            String comments = response.getString("comments");
                            String gender = response.getString("gender");
                            String currentCity = response.getString("currentCity");
                            String nationality = response.getString("nationality");
                            String phoneNumber = response.getString("phoneNumber");
                            String sexo = response.getString("gender");
                            String fecha = response.getString("dateOfBirth");

                            if (email != "null") {
                                edit_email.setText(email);
                            }
                            if (fecha != "null"){
                                edit_fecha.setText(fecha);
                            }

                            if (currentCity != "null"){
                                edit_ciudad.setText(currentCity);
                            }
                            if (phoneNumber != "null") {
                                edit_telefono.setText(phoneNumber);
                            }
                            if (nationality != "null"){
                                edit_nacionalidad.setText(nationality);
                            }
                            if (gender != "null"){
                                edit_sexo.setText(sexo);
                            }
                            if (comments != "null"){
                                edit_comments.setText(comments);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
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
    }
}
