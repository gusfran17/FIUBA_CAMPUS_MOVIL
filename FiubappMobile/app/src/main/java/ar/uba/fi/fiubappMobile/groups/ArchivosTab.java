package ar.uba.fi.fiubappMobile.groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fiubapp.fiubapp.Alumno;
import com.fiubapp.fiubapp.MultipartRequest;
import com.fiubapp.fiubapp.MuroAdapter;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;
import com.fiubapp.fiubapp.dominio.Grupo;
import com.fiubapp.fiubapp.dominio.MensajeMuro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class ArchivosTab extends Fragment {

    private String urlAPI;
    private List<Archivo> archivos = new ArrayList<>();
    private ListView listView;
    private ArchivoAdapter adapter;
    private Context context;
    private final int FILE_SELECT_CODE = 100;
    private final int FILE_DOWNLOAD = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.archivos_tab, container, false);

        String idGrupo = Integer.toString(getArguments().getInt("idGrupo"));

        Button subir_archivo = (Button)view.findViewById(R.id.subir_archivo);

        subir_archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });

        listView = (ListView)view.findViewById(R.id.archivosList);
        adapter = new ArchivoAdapter(getActivity(), archivos, idGrupo);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Archivo archivo = (Archivo)adapterView.getItemAtPosition(i);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + archivo.getName());

                if (file.exists()) {

                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);

                    intent.setDataAndType(Uri.fromFile(file), archivo.getContentType());
                    startActivity(intent);

                }else{
                    Popup.showText(context,"Debe descargar el archivo antes",Toast.LENGTH_LONG).show();
                }

            }
        });

        obtenerArchivos(idGrupo);

        return view;
    }

    private void obtenerArchivos(String idGrupo){

        this.archivos.clear();

        String url = getURLAPI()+"/groups/"+idGrupo+"/files/";

        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(ArchivosTab.class.getSimpleName(), response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                Archivo archivo = new Archivo();

                                archivo.setId(obj.getString("id"));
                                archivo.setContentType(obj.getString("contentType"));
                                archivo.setName(obj.getString("name"));

                                archivos.add(archivo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Popup.showText(context, "Ha ocurrido un error. " +
                                "Probá nuevamente en unos minutos", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", getToken());
                return headers;
            }

        };
        VolleyController.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent ReturnedIntent) {
        super.onActivityResult(requestCode, resultCode,ReturnedIntent);

        final String idGrupo = Integer.toString(getArguments().getInt("idGrupo"));

        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                String filePath = ReturnedIntent.getData().getPath();
                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                Uri selectedfile = ReturnedIntent.getData();

                String mimeType = getMimeType(ReturnedIntent.getData());

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", getToken());

                GroupFileMultipartRequest mPR = new GroupFileMultipartRequest(urlAPI+"/groups/"+idGrupo+"/files", new Response.ErrorListener() {
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
                } , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        obtenerArchivos(idGrupo);

                    }
                }, selectedfile, this.context, headers,mimeType);

                VolleyController.getInstance().addToRequestQueue(mPR);

            }
        }
    }

    public String getMimeType(Uri uri) {
        String type = null;

        if(!uri.toString().contains("content://")) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath());
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
        }
        else{
            type = this.context.getContentResolver().getType(uri);
        }

        return type;
    }

    public static ArchivosTab nuevoGrupo(int idGrupo) {
        ArchivosTab tab = new ArchivosTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo",idGrupo);
        tab.setArguments(args);
        return tab;
    }

    private String getToken(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

    private String getURLAPI(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getURLAPI();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
    }
}
