package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        cargarDatosEducacionSecundaria();
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
        String username = settings.getString("username", null);
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
        String username = settings.getString("username", null);
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
}
