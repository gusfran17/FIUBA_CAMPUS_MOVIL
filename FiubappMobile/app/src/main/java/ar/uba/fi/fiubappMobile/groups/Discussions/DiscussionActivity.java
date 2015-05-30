package ar.uba.fi.fiubappMobile.groups.Discussions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;
import com.fiubapp.fiubapp.dominio.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class DiscussionActivity extends Activity {

    private int id;
    private int groupId;
    private String urlAPI;
    private MessagesAdapter messagesAdapter;
    private List<Message> messagesList = new ArrayList<Message>();
    private ListView messagesListView;
    private static final String TAG = DiscussionActivity.class.getSimpleName();
    private TextView lbl_discussion_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urlAPI = getResources().getString(R.string.urlAPI);

        Intent myIntent = getIntent();
        this.id = myIntent.getIntExtra("discussionId",-1);
        this.groupId = myIntent.getIntExtra("groupId",-1);

        setContentView(R.layout.activity_discussion);
        messagesListView = (ListView) findViewById(R.id.lstVw_messages);
        lbl_discussion_header = (TextView) findViewById(R.id.lbl_discussion_header);
        lbl_discussion_header.setText(myIntent.getStringExtra("discussionName"));

        fillMesaagesList();
        messagesAdapter = new MessagesAdapter(this, messagesList);
        messagesListView.setAdapter(messagesAdapter);

        ImageView btn_mssg_back = (ImageView) findViewById(R.id.btn_mssg_back);
        btn_mssg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnComment = (Button) findViewById(R.id.btnAddMessage);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCreateMessage();
            }
        });
    }

    private void setCreateMessage() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View createDiscussionMessageView = layoutInflater.inflate(R.layout.create_discussion_message, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(createDiscussionMessageView);
        DataAccess dataAccess = new DataAccess(this);
        final String creator = dataAccess.getUserName();
        final EditText edtvw_message = (EditText)createDiscussionMessageView.findViewById(R.id.edtvw_message);
        final Activity activity = this;
        
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Escribir Comentario")
                .setPositiveButton("Comentar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (edtvw_message.getText().toString().equals("")) {
                                    Popup.showText(activity, "El comentario no puede ser vacio.", Toast.LENGTH_LONG).show();
                                    setCreateMessage();
                                } else {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


                                    Message message = new Message();
                                    message.setText(edtvw_message.getText().toString());
                                    message.setCreationDate(simpleDateFormat.format(new Date()));
                                    message.setCreatorUserName(creator);
                                    createMessage(message);
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

    private void createMessage(Message message) {
    }

    private void fillMesaagesList() {
        String requestURL = urlAPI + "/groups/" + groupId + "/discussions/" + id + "/messages";
        JsonArrayRequest messagesReq = new JsonArrayRequest(requestURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Message message = new Message();

                                JSONObject jsonMessage = response.getJSONObject(i);
                                JSONObject jsonCreator = jsonMessage.getJSONObject("creator");
                                message.setCreatorUserName(jsonCreator.get("name") + " " + jsonCreator.get("lastName"));
                                message.setText(jsonMessage.getString("message"));
                                message.setCreationDate(jsonMessage.getString("creationDate"));

                                messagesList.add(message);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        messagesAdapter.notifyDataSetChanged();

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
                SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
                String token = settings.getString("token",null);
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;

            }
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(messagesReq);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
