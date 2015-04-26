package com.fiubapp.fiubapp;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Perfil_personal extends Fragment {

    private String urlAPI = "";
    private String username = "";
    private static final String TAG = Perfil_personal.class.getSimpleName();
    private EmailValidator emailValidator;
    private String fecha;

    private View view = null;

    public void Perfil_personal(String username){
        this.username = username;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.perfil_datos_personales, container, false);


        final EditText header_name = (EditText) view.findViewById(R.id.header_name);
        final EditText header_lastname = (EditText) view.findViewById(R.id.header_lastname);
        final TextView padron = (TextView)view.findViewById(R.id.header_padron);

        final EditText edit_comments = (EditText) view.findViewById(R.id.edit_comentarios);
        final EditText edit_email = (EditText) view.findViewById(R.id.edit_email);
        final EditText edit_telefono = (EditText) view.findViewById(R.id.edit_tel);
        final EditText edit_ciudad = (EditText) view.findViewById(R.id.edit_ciudad);
        final EditText edit_nacionalidad = (EditText) view.findViewById(R.id.edit_nacionalidad);
        final Spinner edit_sexo = (Spinner) view.findViewById(R.id.edit_sex);
        final TextView edit_fecha = (TextView) view.findViewById(R.id.edit_fecha);
        final ImageView edit_button = (ImageView) view.findViewById(R.id.editButton);
        final ImageView edit_name = (ImageView) view.findViewById(R.id.edit_name);
        final ImageView edit_comments_img = (ImageView)view.findViewById(R.id.editButtonComm);

        final TextView profile_name = (TextView)view.findViewById(R.id.profile_name);

        edit_comments.setEnabled(false);
        edit_email.setEnabled(false);
        edit_telefono.setEnabled(false);
        edit_sexo.setEnabled(false);
        edit_ciudad.setEnabled(false);
        edit_nacionalidad.setEnabled(false);
        edit_fecha.setEnabled(false);

        header_name.setEnabled(false);
        header_lastname.setEnabled(false);

        urlAPI = getResources().getString(R.string.urlAPI);

        edit_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etFechaNacimiento = (EditText)v.findViewById(R.id.edit_fecha);
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

                Calendar c = Calendar.getInstance();

                if(etFechaNacimiento.getText() != null && etFechaNacimiento.getText().toString() != null && !etFechaNacimiento.getText().toString().equals("")){
                    try {
                        c.setTime(formatoDelTexto.parse(etFechaNacimiento.getText().toString()));
                    } catch (ParseException e) {
                    }
                }

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                fecha = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                edit_fecha.setText(fecha);

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Listo", dpd);
                dpd.show();
            }
        });

        edit_comments_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_comments.isEnabled()){

                    String comments = edit_comments.getText().toString();


                    Map<String, String> params = new HashMap<String, String>();
                    params.put("comments", comments);

                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.PUT,
                            urlAPI + "/students/" + username,
                            new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    VolleyLog.d(TAG, "Error: " + response.toString());
                                    edit_comments.setEnabled(false);
                                    edit_comments_img.setImageResource(R.drawable.ic_editar);
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
                            SharedPreferences settings = getActivity().getSharedPreferences(
                                    getResources().getString(R.string.prefs_name), 0);
                            String token = settings.getString("token", null);
                            headers.put("Authorization", token);

                            return headers;
                        }

                    };

                    //llamada al PUT
                    VolleyController.getInstance().addToRequestQueue(jsonReq);

                }else{
                    edit_comments_img.setImageResource(R.drawable.ic_save);
                }

                edit_comments.setEnabled(!edit_comments.isEnabled());
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_email.isEnabled()) {

                    String email = edit_email.getText().toString();
                    String gender = edit_sexo.getSelectedItem().toString();
                    String currentCity = edit_ciudad.getText().toString();
                    String nationality = edit_nacionalidad.getText().toString();
                    String phoneNumber = edit_telefono.getText().toString();

                    //si el mail es valido llamo al REST
                    emailValidator = new EmailValidator();
                    if (emailValidator.validate(email)) {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", email);
                        params.put("dateOfBirth", fecha);
                        params.put("phoneNumber", phoneNumber);
                        params.put("currentCity", currentCity);
                        params.put("nationality", nationality);


                        //para evitar que se guarde el primer valor del spinner
                        if (gender.equals("Seleccione un sexo"))
                            gender = "null";

                        params.put("gender", gender);

                        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.PUT,
                                urlAPI + "/students/" + username,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Error: " + response.toString());

                                        edit_button.setImageResource(R.drawable.ic_editar);
                                        edit_ciudad.setEnabled(false);
                                        edit_email.setEnabled(false);
                                        edit_nacionalidad.setEnabled(false);
                                        edit_telefono.setEnabled(false);

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
                                SharedPreferences settings = getActivity().getSharedPreferences(
                                        getResources().getString(R.string.prefs_name), 0);
                                String token = settings.getString("token", null);
                                headers.put("Authorization", token);

                                return headers;
                            }

                        };

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReq);

                        //aviso de mail no valido
                    } else {
                        Popup.showText(getActivity(), "El email no es válido", Toast.LENGTH_LONG).show();
                    }
                }else{
                    edit_button.setImageResource(R.drawable.ic_save);
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

                                        ((PerfilTabs)getActivity()).setText(header_name.getText().toString()
                                                +" "+header_lastname.getText().toString());

                                        edit_name.setImageResource(R.drawable.ic_editar);

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
                                SharedPreferences settings = getActivity().getSharedPreferences(
                                        getResources().getString(R.string.prefs_name), 0);
                                String token = settings.getString("token", null);
                                headers.put("Authorization", token);

                                return headers;
                            }

                        };

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReqName);

                    } else {
                        Popup.showText(getActivity(), "Nombre y/o apellido vacíos", Toast.LENGTH_LONG).show();
                    }

                } else {
                    header_name.setEnabled(!header_name.isEnabled());
                    header_lastname.setEnabled(!header_lastname.isEnabled());
                    edit_name.setImageResource(R.drawable.ic_save);
                }
            }
        });

        SharedPreferences settings = getActivity().getSharedPreferences(
                getResources().getString(R.string.prefs_name), 0);
        if (settings.getBoolean("isExchange", false)) {
            username = "I" + settings.getString("username", null);
        } else {
            username = settings.getString("username", null);
        }

        //obtener datos
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                urlAPI + "/students/" + username,
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
                            padron.setText(username);

                            ((PerfilTabs)getActivity()).setText(name+" "+lastName);

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
                            if (fecha != "null") {
                                edit_fecha.setText(fecha);
                            }

                            if (currentCity != "null") {
                                edit_ciudad.setText(currentCity);
                            }
                            if (phoneNumber != "null") {
                                edit_telefono.setText(phoneNumber);
                            }
                            if (nationality != "null") {
                                edit_nacionalidad.setText(nationality);
                            }
                            if (gender != "null") {
                                if (gender.equals("Femenino")) {
                                    edit_sexo.setSelection(1);
                                } else if (gender.equals("Masculino"))
                                    edit_sexo.setSelection(2);
                            }
                            if (comments != "null") {
                                edit_comments.setText(comments);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                SharedPreferences settings = getActivity().getSharedPreferences(
                        getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token", null);
                headers.put("Authorization", token);

                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonReq);
        return view;
    }

}