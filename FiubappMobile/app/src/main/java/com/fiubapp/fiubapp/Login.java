package com.fiubapp.fiubapp;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.login);

        final EditText edit_padron = (EditText)findViewById(R.id.reg_padron);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnSoyNuevo = (Button) findViewById(R.id.btnSoyNuevo);
        final EditText edit_pass = (EditText)findViewById(R.id.reg_password);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_intercambio);

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    edit_padron.setHint(R.string.pasaporte);
                else
                    edit_padron.setHint(R.string.numeroPadron);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String password = edit_pass.getText().toString();
                String username = edit_padron.getText().toString();

                if (!username.equals("") && !password.equals("")){
                    if (password.length() >= 8) {
                        login(username, password);
                    }else{
                        Toast.makeText(Login.this, "La contraseña debe tener 8 o más caracteres", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Login.this, edit_padron.getHint() +
                            " y/o contraseña vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSoyNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getBaseContext(), Register1.class);
                startActivity(i);
            }
        });
    }

    public void login(final String usuario, String clave) {

        final EditText edit_padron = (EditText)findViewById(R.id.reg_padron);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_intercambio);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getResources().getString(R.string.urlAPI)+"/sessions/students";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("userName", usuario);
            jsonParams.put("password", clave);
            jsonParams.put("isExchangeStudent", checkBox.isChecked());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String token = response.getString("token");

                            // Guardar el token para que las otras activities tengan acceso
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", token);
                            editor.commit();

                            Intent i = new Intent(getBaseContext(), Principal.class);
                            i.putExtra("username", usuario);
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, edit_padron.getHint() + " y/o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsObjRequest);
    }
}