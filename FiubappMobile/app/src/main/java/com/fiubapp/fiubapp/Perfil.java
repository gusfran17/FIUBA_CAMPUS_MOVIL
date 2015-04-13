package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        cargarDatosEducacionSecundaria();
        final EditText header_name = (EditText)findViewById(R.id.header_name);
        final EditText header_lastname = (EditText)findViewById(R.id.header_lastname);
        final TextView profile_name = (TextView)findViewById(R.id.profile_name);

        final EditText edit_comments = (EditText)findViewById(R.id.edit_comentarios);
        final EditText edit_email = (EditText)findViewById(R.id.edit_email);
        final EditText edit_telefono = (EditText)findViewById(R.id.edit_tel);
        final EditText edit_ciudad = (EditText)findViewById(R.id.edit_ciudad);
        final EditText edit_nacionalidad = (EditText)findViewById(R.id.edit_nacionalidad);
        final Spinner edit_sexo = (Spinner)findViewById(R.id.edit_sex);
        final TextView edit_fecha = (TextView)findViewById(R.id.edit_fecha);
        final ImageView edit_button = (ImageView)findViewById(R.id.editButton);
        final ImageView edit_name = (ImageView)findViewById(R.id.edit_name);

        edit_email.setEnabled(false);
        edit_telefono.setEnabled(false);
        edit_sexo.setEnabled(false);
        edit_ciudad.setEnabled(false);
        edit_nacionalidad.setEnabled(false);
        edit_fecha.setEnabled(false);

        header_name.setEnabled(false);
        header_lastname.setEnabled(false);

        urlAPI = getResources().getString(R.string.urlAPI);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_email.isEnabled()){

                    String email = edit_email.getText().toString();
                    String comments = edit_comments.getText().toString();
                    String gender = edit_sexo.getSelectedItem().toString();
                    String currentCity = edit_ciudad.getText().toString();
                    String nationality = edit_nacionalidad.getText().toString();
                    String phoneNumber = edit_telefono.getText().toString();

                    //si el mail es valido llamo al REST
                    emailValidator = new EmailValidator();
                    if (emailValidator.validate(email)) {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", email);
                        params.put("dateOfBirth",fecha);
                        params.put("phoneNumber",phoneNumber);
                        params.put("currentCity",currentCity);
                        params.put("nationality",nationality);
                        params.put("comments",comments);
                        params.put("gender",gender);

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

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReq);

                        //aviso de mail no valido
                    }else{
                        Toast.makeText(Perfil.this, "El email no es válido", Toast.LENGTH_LONG).show();
                    }
                }

                edit_email.setEnabled(!edit_email.isEnabled());
                edit_telefono.setEnabled(!edit_telefono.isEnabled());
                edit_ciudad.setEnabled(!edit_ciudad.isEnabled());
                edit_nacionalidad.setEnabled(!edit_nacionalidad.isEnabled());
                edit_sexo.setEnabled(!edit_sexo.isEnabled());
                edit_fecha.setEnabled(!edit_fecha.isEnabled());

            }
        });

        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (header_name.isEnabled()) {
                    //obtengo todos los datos actuales
                    String name = header_name.getText().toString();
                    String lastName = header_lastname.getText().toString();

                    if (!name.equals("") && !lastName.equals("")) {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", name);
                        params.put("lastName", lastName);

                        JsonObjectRequest jsonReqName = new JsonObjectRequest(Request.Method.PUT,
                                urlAPI + "/students/" + username,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Response: " + response.toString());

                                        header_name.setEnabled(false);
                                        header_lastname.setEnabled(false);

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

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReqName);

                    } else {
                        Toast.makeText(Perfil.this, "Nombre y/o apellido vacíos", Toast.LENGTH_LONG).show();
                    }

                } else {
                    header_name.setEnabled(!header_name.isEnabled());
                    header_lastname.setEnabled(!header_lastname.isEnabled());
                }
            }
        });

        SharedPreferences settings = this.getSharedPreferences(
                getResources().getString(R.string.prefs_name), 0);
        if (settings.getBoolean("isExchange",false)){
            username = "I"+settings.getString("username",null);
        }else{
            username = settings.getString("username",null);
        }

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
                                if (gender.equals("Femenino")) {
                                    edit_sexo.setSelection(0);
                                    //edit_sexo.s
                                }
                                else if (gender.equals("Masculino"))
                                    edit_sexo.setSelection(1);
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

    public void onClickImgEditar(View v) {
        final ImageView imgEditar = (ImageView)findViewById(R.id.imgEditar);

        final EditText fechaInicio = (EditText)findViewById(R.id.etFechaInicio);
        final EditText fechaFin = (EditText)findViewById(R.id.etFechaFin);
        final EditText titulo = (EditText)findViewById(R.id.etTitulo);
        final EditText escuela = (EditText)findViewById(R.id.etEscuela);

        fechaInicio.setEnabled(!fechaInicio.isEnabled());
        fechaFin.setEnabled(!fechaFin.isEnabled());
        titulo.setEnabled(!titulo.isEnabled());
        escuela.setEnabled(!escuela.isEnabled());

        if(titulo.isEnabled()) {
            imgEditar.setImageResource(R.drawable.ic_editar_selected);
        }
        else { // Si no está habilitado significa que termino de editar, se guarda la información

            if(validaIngresoDatosEducacionSecundaria()) {
                imgEditar.setImageResource(R.drawable.ic_editar);
                guardarDatosEducacionSecundaria();
            }
            else{
                fechaInicio.setEnabled(true);
                fechaFin.setEnabled(true);
                titulo.setEnabled(true);
                escuela.setEnabled(true);
            }
        }
    }

    public boolean validaIngresoDatosEducacionSecundaria(){
        final EditText etFechaInicio = (EditText)findViewById(R.id.etFechaInicio);
        final EditText etFechaFin = (EditText)findViewById(R.id.etFechaFin);

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

        Date actual = new Date();
        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = formatoDelTexto.parse(etFechaInicio.getText().toString());
            fechaFin = formatoDelTexto.parse(etFechaFin.getText().toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        if(!fechaInicio.before(fechaFin) || !fechaFin.before(actual)){
            Toast.makeText(Perfil.this, "Verifique que la fecha desde sea menor a la fecha hasta y no sean futuras", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void onClickFechaInicio(View v) {

        final EditText fechaInicio = (EditText)findViewById(R.id.etFechaInicio);

        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fechaInicio.setText(String.format("%02d", dayOfMonth) + "/"
                                + String.format("%02d", (monthOfYear + 1)) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void onClickFechaFin(View v) {

        final EditText fechaFin = (EditText)findViewById(R.id.etFechaFin);

        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fechaFin.setText(String.format("%02d", dayOfMonth) + "/"
                                + String.format("%02d", (monthOfYear + 1)) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void cargarDatosEducacionSecundaria() {

        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        String username = null;
        if (settings.getBoolean("isExchange",false)){
            username = "I"+settings.getString("username",null);
        }else{
            username = settings.getString("username",null);
        }
        final String token = settings.getString("token", null);

        final EditText etFechaInicio = (EditText)findViewById(R.id.etFechaInicio);
        final EditText etFechaFin = (EditText)findViewById(R.id.etFechaFin);
        final EditText etTitulo = (EditText)findViewById(R.id.etTitulo);
        final EditText etEscuela = (EditText)findViewById(R.id.etEscuela);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/highSchool";

        JSONObject jsonParams = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String titulo = response.getString("degree");
                            String escuela = response.getString("schoolName");
                            String fechaInicio = response.getString("dateFrom");
                            String fechaFin = response.getString("dateTo");

                            etFechaInicio.setText(fechaInicio);
                            etFechaFin.setText(fechaFin);
                            etTitulo.setText(titulo);
                            etEscuela.setText(escuela);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //parseo la respuesta del server para obtener JSON
                        String body = null;
                        try {
                            body = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));

                            JSONObject JSONBody = new JSONObject(body);
                            String codigoError = JSONBody.getString("code");

                            if(!codigoError.equals("4002")){
                                iniciarSesionNuevamente(JSONBody.getString("message"));
                            }
                        } catch (UnsupportedEncodingException e) {
                            iniciarSesionNuevamente(null);
                        } catch (JSONException e) {
                            iniciarSesionNuevamente(null);
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }

    public void iniciarSesionNuevamente(String mensaje){

        String msg = "Debe iniciar sesión nuevamente";

        if(mensaje != null)
            msg = mensaje;

        Toast.makeText(Perfil.this, msg, Toast.LENGTH_LONG).show();

        Intent i = new Intent(getBaseContext(), Login.class);
        startActivity(i);
    }

    public void guardarDatosEducacionSecundaria(){
        guardarOACtualizarDatosEducacionSecundaria(Request.Method.PUT);
    }

    public void guardarOACtualizarDatosEducacionSecundaria(int guardarActualizar){

        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        String username = null;
        if (settings.getBoolean("isExchange",false)){
            username = "I"+settings.getString("username",null);
        }else{
            username = settings.getString("username",null);
        }
        final String token = settings.getString("token", null);

        final EditText fechaInicio = (EditText)findViewById(R.id.etFechaInicio);
        final EditText fechaFin = (EditText)findViewById(R.id.etFechaFin);
        final EditText titulo = (EditText)findViewById(R.id.etTitulo);
        final EditText escuela = (EditText)findViewById(R.id.etEscuela);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/highSchool";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("degree", titulo.getText());
            jsonParams.put("schoolName", escuela.getText());
            jsonParams.put("dateFrom", fechaInicio.getText());
            jsonParams.put("dateTo", fechaFin.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(guardarActualizar, url, jsonParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Perfil.this, "Datos de educación secundaria guardados correctamente", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //parseo la respuesta del server para obtener JSON
                        String body = null;
                        try {
                            body = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));

                            JSONObject JSONBody = new JSONObject(body);
                            String codigoError = JSONBody.getString("code");

                            if(codigoError.equals("4002")) {
                                guardarOACtualizarDatosEducacionSecundaria(Request.Method.POST);
                            }
                            else {
                                iniciarSesionNuevamente(JSONBody.getString("message"));
                            }
                        } catch (JSONException e) {
                            iniciarSesionNuevamente(null);
                        } catch (UnsupportedEncodingException e) {
                            iniciarSesionNuevamente(null);
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }
public void onClickBack(View v){
        final ImageView back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickFechaNacimiento(View v) {

        final EditText fechaNacimiento = (EditText)findViewById(R.id.edit_fecha);

        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fecha = dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year;
                        fechaNacimiento.setText(fecha);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
}
