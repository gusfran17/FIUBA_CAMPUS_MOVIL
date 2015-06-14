package ar.uba.fi.fiubappMobile.groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;
import com.fiubapp.fiubapp.dominio.Grupo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class ArchivoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Archivo> archivoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
    private String idGrupo;

    public ArchivoAdapter(Activity activity, List<Archivo> archivoItems, String idGrupo) {
        this.activity = activity;
        this.archivoItems = archivoItems;
        this.idGrupo = idGrupo;
    }

    @Override
    public int getCount() {
        return archivoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return archivoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View archivoFilaView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (archivoFilaView == null)
            archivoFilaView = inflater.inflate(R.layout.archivos_row, null);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();

        TextView nombre = (TextView) archivoFilaView.findViewById(R.id.archivo);
        ImageView download_image = (ImageView) archivoFilaView.findViewById(R.id.download);

        final Archivo archivo = archivoItems.get(position);
        nombre.setText(archivo.getName());

        download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descargarArchivo(archivo);
            }
        });

        return archivoFilaView;
    }

    private void descargarArchivo(final Archivo archivo){

        String url = getURLAPI()+"/groups/"+idGrupo+"/files/"+archivo.getId();
        final String filename = archivo.getName();

        File fileTest = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);

        if (fileTest.exists()){
            Popup.showText(activity,"El archivo ya se encuentra descargado",Toast.LENGTH_LONG).show();
        }else {

            StringRequest jsonReq = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            Log.d(ArchivosTab.class.getSimpleName(), "OK");

                            try {

                                FileWriter file = new FileWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename));

                                String decoded = Base64.encodeToString(response.toString().getBytes(),0);

                                oos.writeObject(decoded.getBytes());
                                oos.close();

                                /*try {
                                    //file.write(response);
                                    Popup.showText(activity,"El archivo se ha descargado",Toast.LENGTH_LONG).show();

                                } catch (IOException e) {
                                    e.printStackTrace();

                                } finally {
                                    file.flush();
                                    file.close();
                                    oos.close();
                                }*/

                            } catch (IOException e) {

                            }

                            notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(ArchivosTab.class.getSimpleName(), error.toString());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", getToken());
                    return headers;
                }

            };
            VolleyController.getInstance().addToRequestQueue(jsonReq);
        }

    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getURLAPI();
    }
}

