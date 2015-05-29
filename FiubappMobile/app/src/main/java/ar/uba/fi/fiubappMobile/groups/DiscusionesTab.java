package ar.uba.fi.fiubappMobile.groups;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiubapp.fiubapp.VolleyController;
import com.fiubapp.fiubapp.dominio.Discussion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.uba.fi.fiubappMobile.groups.Discussions.DiscussionActivity;
import ar.uba.fi.fiubappMobile.groups.Discussions.DiscussionsAdapter;
import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class DiscusionesTab extends Fragment {

    private String urlAPI;
    private static final String TAG = DiscusionesTab.class.getSimpleName();
    private DiscussionsAdapter discussionsAdapter;
    private List<Discussion> discussionList = new ArrayList<Discussion>();
    private ListView discussionsListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View regularSearchView = inflater.inflate(R.layout.discusiones_grupo_tab, container, false);

        discussionsListView = (ListView) regularSearchView.findViewById(R.id.lstVw_discussions);

        fillDiscussionsList();

        discussionsAdapter = new DiscussionsAdapter(getActivity(), discussionList);
        discussionsListView.setAdapter(discussionsAdapter);

        Button btnCreateDiscussion = (Button)regularSearchView.findViewById(R.id.btnCreateDiscussion);
        btnCreateDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCreateDiscussion("","");
            }
        });

        return regularSearchView;

    }

    private void setCreateDiscussion(String discussionName, String firstMessage) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View createDiscussionView = layoutInflater.inflate(R.layout.create_discussion, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(createDiscussionView);

        final EditText edtvw_discussion_name = (EditText)createDiscussionView.findViewById(R.id.edtvw_discussion_name);
        final EditText edtvw_first_message = (EditText)createDiscussionView.findViewById(R.id.edtvw_first_message);

        edtvw_discussion_name.setText(discussionName);
        edtvw_first_message.setText(firstMessage);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Iniciar Discusión")
                .setPositiveButton("Iniciar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (edtvw_discussion_name.getText().toString().equals("")){
                                    Popup.showText(getActivity(), "El nombre de la discusión no puede ser vacio.", Toast.LENGTH_LONG).show();
                                    setCreateDiscussion("",edtvw_first_message.getText().toString());
                                } else if (edtvw_first_message.getText().toString().equals("")){
                                    Popup.showText(getActivity(), "El primer mensaje de la discusión no puede ser vacio.", Toast.LENGTH_LONG).show();
                                    setCreateDiscussion(edtvw_discussion_name.getText().toString(),"");
                                } else {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    DataAccess dataAccess = new DataAccess(getActivity());
                                    String userName = dataAccess.getUserName();

                                    Discussion discussion = new Discussion();
                                    discussion.setDiscussionName(edtvw_discussion_name.getText().toString());
                                    discussion.setCreationDate(simpleDateFormat.format(new Date()));
                                    discussion.setCreatorUserName(userName);
                                    createDiscussion(discussion, edtvw_first_message.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void createDiscussion(final Discussion discussion, String firstMessage) {
        int groupId = (int) getArguments().get("idGrupo");

        String requestURL = urlAPI + "/groups/" + groupId + "/discussions";
        JSONObject jsonNewDiscussion = new JSONObject();

        try {
            jsonNewDiscussion.put("discussionName", discussion.getDiscussionName());
            jsonNewDiscussion.put("creatorUserName", discussion.getCreatorUserName());
            jsonNewDiscussion.put("firstMessage", firstMessage);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,
                requestURL,
                jsonNewDiscussion,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Discussion discussion = new Discussion();
                        DataAccess dataAccess = new DataAccess(getActivity());
                        String userName = dataAccess.getUserName();
                        try {

                            discussion.setId(response.getInt("id"));
                            discussion.setCreationDate(response.getString("creationDate"));
                            discussion.setDiscussionName(response.getString("discussionName"));
                            discussion.setCreatorUserName(response.getString("creatorUserName"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        discussionList.add(discussion);
                        discussionsAdapter.notifyDataSetChanged();
                        Popup.showText(getActivity(), "Datos de discusión guardados correctamente", Toast.LENGTH_LONG).show();
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
                            String errorCode = JSONBody.getString("code");
                            String errorMessage = JSONBody.getString("message");
                            Popup.showText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                            setCreateDiscussion("","");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token", null);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        VolleyController.getInstance().addToRequestQueue(jsObjRequest);
    }

    private void fillDiscussionsList() {
        int groupId = (int) getArguments().get("idGrupo");
        String requestURL = urlAPI + "/groups/" + groupId + "/discussions";
        JsonArrayRequest studentReq = new JsonArrayRequest(requestURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Discussion discussion = new Discussion();

                                JSONObject jsonDiscuccion = response.getJSONObject(i);
                                JSONObject jsonCreator = jsonDiscuccion.getJSONObject("creator");

                                discussion.setCreatorUserName(jsonCreator.getString("name")+ " " + jsonCreator.getString("lastName"));
                                discussion.setDiscussionName(jsonDiscuccion.getString("discussionName"));
                                discussion.setCreationDate(jsonDiscuccion.getString("creationDate"));
                                discussion.setId(jsonDiscuccion.getInt("id"));

                                discussionList.add(discussion);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        discussionsAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;

            }
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(studentReq);

    }

    public static DiscusionesTab nuevoGrupo(int idGrupo) {
        DiscusionesTab tab = new DiscusionesTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo",idGrupo);
        tab.setArguments(args);
        return tab;
    }

}
