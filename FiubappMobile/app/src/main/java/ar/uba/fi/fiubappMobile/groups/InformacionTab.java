package ar.uba.fi.fiubappMobile.groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fiubapp.fiubapp.GruposTab;
import com.fiubapp.fiubapp.MultipartRequest;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class InformacionTab extends Fragment {

    private String urlAPI;
    private View view = null;
    private static final int SELECT_PHOTO = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.informacion_grupo_tab, container, false);

        obtenerGrupo(getArguments().getInt("idGrupo"));

        return view;
    }

    private void configurarEditNombreGrupo(){

        final EditText etNombreGrupo = (EditText) view.findViewById(R.id.name_group);
        final EditText etDummy = (EditText) view.findViewById(R.id.etDummy);
        final ImageView ivEditar = (ImageView) view.findViewById(R.id.edit_name);

        ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNombreGrupo.isEnabled()) {
                    //obtengo todos los datos actuales
                    String nombreGrupo = etNombreGrupo.getText().toString();

                    etNombreGrupo.setBackgroundColor(Color.TRANSPARENT);

                    if (!nombreGrupo.equals("")) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", nombreGrupo);

                        JsonObjectRequest jsonReqName = new JsonObjectRequest(Request.Method.PUT,
                                urlAPI + "/groups/" + getArguments().getInt("idGrupo"),
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        etNombreGrupo.setEnabled(false);

                                        (((GruposTabs) getActivity())).setText(etNombreGrupo.getText().toString());

                                        ivEditar.setImageResource(R.drawable.ic_editar);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                                String token = settings.getString("token", null);
                                headers.put("Authorization", token);
                                return headers;
                            }
                        };

                        //llamada al PUT
                        VolleyController.getInstance().addToRequestQueue(jsonReqName);

                    } else {
                        Popup.showText(getActivity(), getResources().getString(R.string.nombre_vacio), Toast.LENGTH_LONG).show();
                    }

                } else {
                    etNombreGrupo.setEnabled(!etNombreGrupo.isEnabled());
                    ivEditar.setImageResource(R.drawable.ic_save);
                    etNombreGrupo.setBackgroundDrawable(etDummy.getBackground());
                }
            }
        });
    }

    private void configurarClickImagen(boolean soyElDuenio, final int idGrupo){

        ImageView ivImagenGrupo = (ImageView)view.findViewById(R.id.image_group);

        if(soyElDuenio) {
            ivImagenGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                }
            });

            ivImagenGrupo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String url = urlAPI + "/groups/" + idGrupo + "/picture";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    return true;
                }
            });
        }
        else{
            ivImagenGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = urlAPI + "/groups/" + idGrupo + "/picture";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
    }

    private void obtenerGrupo(final int idGrupo){

        final SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);

        final EditText etNombreGrupo = (EditText) view.findViewById(R.id.name_group);
        final TextView tvCantidadMiembros = (TextView) view.findViewById(R.id.count_group);
        final ImageView ivEditar = (ImageView) view.findViewById(R.id.edit_name);
        final ImageView ivImagenGrupo = (ImageView)view.findViewById(R.id.image_group);
        final TextView tvFechaCreacion = (TextView) view.findViewById(R.id.tvFechaCreacion);
        final TextView tvCreadoPor = (TextView) view.findViewById(R.id.tvCreadoPor);

        ImageRequest ir = new ImageRequest(urlAPI+"/groups/" + idGrupo + "/picture", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivImagenGrupo.setImageBitmap(response);
            }
        }, 0, 0, null, null);

        //obtener datos
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                urlAPI + "/groups/" + idGrupo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nombreGrupo = response.getString("name");
                            String fechaCreacion = response.getString("creationDate");
                            int cantidadMiembros = response.getInt("amountOfMembers");
                            JSONObject duenio = response.getJSONObject("owner");
                            String userNameDuenio = duenio.getString("userName");
                            String nombreDuenio = duenio.getString("name");
                            String apellidoDuenio = duenio.getString("lastName");

                            etNombreGrupo.setText(nombreGrupo);
                            tvFechaCreacion.setText(fechaCreacion);
                            tvCantidadMiembros.setText(cantidadMiembros + " miembros");
                            tvCreadoPor.setText(nombreDuenio + " " + apellidoDuenio);

                            String userNameUsuario = settings.getString("username", null);

                            boolean soyElDuenio = false;

                            etNombreGrupo.setBackgroundColor(Color.TRANSPARENT);
                            etNombreGrupo.setEnabled(false);

                            if (userNameDuenio.equals(userNameUsuario)) {
                                ivEditar.setVisibility(View.VISIBLE);
                                configurarEditNombreGrupo();
                                soyElDuenio = true;
                            }
                            else {
                                ivEditar.setVisibility(View.INVISIBLE);
                            }

                            configurarClickImagen(soyElDuenio, idGrupo);

                        } catch (Exception e) { }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
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

        final SharedPreferences settings = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.prefs_name), 0);
        final ImageView ivImagenGrupo = (ImageView)view.findViewById(R.id.image_group);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    Bitmap image = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //para validar tamaño de imagen
                    // First decode with inJustDecodeBounds=true to check dimensions
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;

                    BitmapFactory.decodeStream(imageStream,null,options);

                    try {
                        imageStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Calculate inSampleSize
                    options.inSampleSize = calculateInSampleSize(options, 90, 90);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;

                    File file = new File(getRealPathFromURI(getActivity(), selectedImage));
                    double imageSize = (file.length() / 1024) / 1024;

                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //si la imagen pesa menos de 10MB
                    if (imageSize <= 10) {
                        image = BitmapFactory.decodeStream(imageStream,null, options);
                        ivImagenGrupo.setImageBitmap(image);

                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", settings.getString("token", null));

                        MultipartRequest mPR = new MultipartRequest(urlAPI + "/groups/" + getArguments().getInt("idGrupo") + "/picture", new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    // TODO Auto-generated method stub
                                    Log.d("Error", error.toString());
                                    String responseBody = null;
                                    JSONObject jsonObject = null;
                                    try {
                                        responseBody = new String(error.networkResponse.data, "utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                    }
                                    try {
                                        jsonObject = new JSONObject(responseBody);
                                    } catch (JSONException e) {
                                    }
                                }
                                catch(Exception e){}
                            }
                        } , new Response.Listener<String>() {
                            @Override
                            public void onResponse(String arg0) {
                                // TODO Auto-generated method stub
                                //Log.d("Success", arg0.toString());
                            }
                        }, selectedImage, getActivity(), headers);

                        VolleyController.getInstance().addToRequestQueue(mPR);

                    } else {
                        Popup.showText(getActivity(), getResources().getString(R.string.mensaje_validar_tamanio_imagen), Toast.LENGTH_LONG).show();
                    }

                }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static InformacionTab nuevoGrupo(int idGrupo) {
        InformacionTab tab = new InformacionTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo",idGrupo);
        tab.setArguments(args);
        return tab;
    }
}
