package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class Perfil_personal extends Fragment {

    private static final int SELECT_PHOTO = 100;
    private String urlAPI = "";
    private String usernamePrefs = "";
    private static final String TAG = Perfil_personal.class.getSimpleName();
    private EmailValidator emailValidator;
    private PhoneNumberValidator phoneValidator;
    private String fecha;
    private Context context;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    private FragmentActivity perfilTabs;

    private View view = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.perfil_datos_personales, container, false);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();

        usernamePrefs = getUsername();
        perfilTabs = getActivity();

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
        final ImageView profile_img = (ImageView)view.findViewById(R.id.image_profile);

        final TextView profile_name = (TextView)view.findViewById(R.id.profile_name);


        urlAPI = getResources().getString(R.string.urlAPI);

        VolleyController.getInstance().getRequestQueue().getCache().remove(urlAPI+"/students/"+"80001"+"/picture");

        edit_comments.setEnabled(false);
        edit_email.setEnabled(false);
        edit_telefono.setEnabled(false);
        edit_sexo.setEnabled(false);
        edit_ciudad.setEnabled(false);
        edit_nacionalidad.setEnabled(false);
        edit_fecha.setEnabled(false);

        header_name.setEnabled(false);
        header_lastname.setEnabled(false);

        //para mostrar el perfil de un alumno
        if (getArguments() != null) {

            edit_button.setVisibility(View.INVISIBLE);
            edit_name.setVisibility(View.INVISIBLE);
            edit_comments_img.setVisibility(View.INVISIBLE);

            if (!getArguments().getBoolean("isMyMate")) {
                header_name.setText(getArguments().getString("name"));
                header_lastname.setText(getArguments().getString("lastName"));

                boolean isIntercambio = getArguments().getBoolean("isExchange");
                String padron_pasaporte = getArguments().getString("userName");
                if (padron_pasaporte.startsWith("I"))
                    padron.setText(padron_pasaporte.substring(1,padron_pasaporte.length()));
                else padron.setText(padron_pasaporte);

                String comentarios = getArguments().getString("comments");
                if (comentarios.equals("null")) comentarios = "";
                edit_comments.setText(comentarios);

                RelativeLayout rel_layout_header = (RelativeLayout)view.findViewById(R.id.headerDatos);
                rel_layout_header.setVisibility(View.INVISIBLE);

                RelativeLayout rel_layout_datos = (RelativeLayout)view.findViewById(R.id.contentDatos);
                rel_layout_datos.setVisibility(View.INVISIBLE);

                //request para la imagen de perfil
                ImageRequest ir = new ImageRequest(urlAPI+"/students/"+padron_pasaporte+"/picture", new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {

                        profile_img.setImageBitmap(response);

                    }
                }, 0, 0, null, null);

                VolleyController.getInstance().addToRequestQueue(ir);

            }else{
                getUserData(getArguments().getString("userName"));
            }

            profile_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = urlAPI+"/students/"+getArguments().getString("userName")+"/picture";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            return view;
        }

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

                DatePickerDialog dpd = new DatePickerDialog(((FragmentActivity)context),
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
                            urlAPI + "/students/" + usernamePrefs,
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
                            SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(
                                    getResources().getString(R.string.prefs_name), 0);
                            String token = settings.getString("token", null);
                            headers.put("Authorization", token);

                            return headers;
                        }

                    };

                    VolleyController.getInstance().addToRequestQueue(jsonReq);
                    edit_comments.clearFocus();

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
                    phoneValidator = new PhoneNumberValidator();
                    if (emailValidator.validate(email)&&((phoneValidator.validate(phoneNumber)))||(phoneNumber.equals(""))) {

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
                                urlAPI + "/students/" + usernamePrefs,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Error: " + response.toString());

                                        edit_email.clearFocus();
                                        edit_telefono.clearFocus();
                                        edit_nacionalidad.clearFocus();
                                        edit_ciudad.clearFocus();

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
                                SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(
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
                        getUserData(usernamePrefs);
                        edit_button.setImageResource(R.drawable.ic_editar);
                        if (!emailValidator.validate(email)){
                            Popup.showText(context, "El email no es válido", Toast.LENGTH_LONG).show();
                        } else{
                            Popup.showText(context, "El numero de telefono no es válido", Toast.LENGTH_LONG).show();
                        }
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
                if (!edit_email.isEnabled()) edit_button.setImageResource(R.drawable.ic_editar);
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
                                urlAPI + "/students/" + usernamePrefs,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Response: " + response.toString());

                                        header_name.setEnabled(false);
                                        header_lastname.setEnabled(false);

                                        (((PerfilTabs)context)).setText(header_name.getText().toString()
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
                                SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(
                                        getResources().getString(R.string.prefs_name), 0);
                                String token = settings.getString("token", null);
                                headers.put("Authorization", token);

                                return headers;
                            }

                        };

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReqName);

                    } else {
                        Popup.showText(context, "Nombre y/o apellido vacíos", Toast.LENGTH_LONG).show();
                    }

                } else {
                    header_name.setEnabled(!header_name.isEnabled());
                    header_lastname.setEnabled(!header_lastname.isEnabled());
                    edit_name.setImageResource(R.drawable.ic_save);
                }
            }
        });

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        getUserData(usernamePrefs);

        return view;
    }

    private void getUserData(final String username){

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
        final ImageView thumbnail = (ImageView)view.findViewById(R.id.image_profile);

		//request para la imagen de perfil
        ImageRequest ir = new ImageRequest(urlAPI+"/students/"+username+"/picture", new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {

                thumbnail.setImageBitmap(response);

            }
        }, 0, 0, null, null);

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

                            //si el perfil es el propio
                            if (username.equals(usernamePrefs))
                                ((PerfilTabs)perfilTabs).setText(name+" "+lastName);
                            else ((PerfilTabsCompanero)perfilTabs).setText(name+" "+lastName);

                            String email = response.getString("email");
                            String comments = response.getString("comments");
                            String gender = response.getString("gender");
                            String currentCity = response.getString("currentCity");
                            String nationality = response.getString("nationality");
                            String phoneNumber = response.getString("phoneNumber");
                            String fecha = response.getString("dateOfBirth");
                            String profileURL = response.getString("profilePicture");
                            boolean isIntercambio = response.getBoolean("isExchangeStudent");

                            if (isIntercambio)
                                padron.setText(username.substring(1,username.length()));
                            else padron.setText(username);

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
                SharedPreferences settings = ((FragmentActivity)context).getSharedPreferences(
                        getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token", null);
                headers.put("Authorization", token);

                return headers;

            }
        };

        VolleyController.getInstance().addToRequestQueue(ir);
        VolleyController.getInstance().addToRequestQueue(jsonReq);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        final ImageView profile_img = (ImageView)this.view.findViewById(R.id.image_profile);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    Bitmap image = null;
                    try {
                        imageStream = this.context.getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    image = BitmapFactory.decodeStream(imageStream);
                    profile_img.setImageBitmap(image);

                    //TODO llamar a volley para el POST

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", getToken());

                    MultipartRequest mPR = new MultipartRequest(urlAPI+"/students/"+usernamePrefs+"/picture", new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("Error", error.toString());
                            String responseBody = null;
                            JSONObject jsonObject = null;
                            try {
                                responseBody = new String( error.networkResponse.data, "utf-8" );
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObject = new JSONObject( responseBody );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Popup.showText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();

                        }
                    } , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String arg0) {
                            // TODO Auto-generated method stub
                            //Log.d("Success", arg0.toString());
                        }
                    }, selectedImage, this.context, headers);

                    VolleyController.getInstance().addToRequestQueue(mPR);
                }
        }
    }

    public static Perfil_personal newContact(Alumno companero) {

        Perfil_personal perfil = new Perfil_personal();

        Bundle args = new Bundle();
        args.putString("name",companero.getNombre());
        args.putString("lastName",companero.getApellido());
        args.putString("userName",companero.getUsername());
        args.putString("comments",companero.getComentario());
        args.putBoolean("isMyMate",companero.isMyMate());
        args.putBoolean("isExchange",companero.isIntercambio());

        perfil.setArguments(args);

        return perfil;

    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
    }

    private Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}