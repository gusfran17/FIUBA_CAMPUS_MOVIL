package ar.uba.fi.fiubappMobile.Jobs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.Perfil_empleo;
import com.fiubapp.fiubapp.Popup;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.utils.DataAccess;

/**
 * Created by Gustavo.Franco on 01/05/2015.
 */
public class JobAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Job> jobItems;

    public JobAdapter(Activity activity, List<Job> jobItems) {
        this.activity = activity;
        this.jobItems = jobItems;
    }


    @Override
    public int getCount() {
        return this.jobItems.size();
    }

    @Override
    public Object getItem(int i) {
        return this.jobItems.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.job_list_row, null);

        TextView job_firm = (TextView) convertView.findViewById(R.id.edt_job_header);
        TextView  job_startdate = (TextView) convertView.findViewById(R.id.edt_job_startdate);
        TextView job_enddate = (TextView) convertView.findViewById(R.id.edt_job_enddate);
        TextView job_description = (TextView) convertView.findViewById(R.id.edt_job_desc);

        ImageView img_delete_job = (ImageView) convertView.findViewById(R.id.img_delete_job);
        ImageView img_edit_job = (ImageView) convertView.findViewById(R.id.img_edit_job);

        Job job = jobItems.get(position);

        if (!job.isCanBeRemoved()){
            img_delete_job.setVisibility(View.INVISIBLE);
            img_edit_job.setVisibility(View.INVISIBLE);
        }

        img_delete_job.setOnClickListener(new imageDeleteViewClickListener(position));
        img_edit_job.setOnClickListener(new imageEditViewClickListener(position));



        job_firm.setText(job.getFirm());
        job_startdate.setText(job.getStartdate());
        if (job.getEnddate().equals("")){
            job_enddate.setText("Actualidad");
        }else {
            job_enddate.setText(job.getEnddate());
        }
        job_description.setText(job.getDescription());

        return convertView;
    }

    class imageDeleteViewClickListener implements View.OnClickListener {
        int position;

        public imageDeleteViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {
                deleteJob(position);
            }
        }
    }


    class imageEditViewClickListener implements View.OnClickListener {
        int position;

        public imageEditViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {
                setEditJob(position);
            }
        }
    }

    private void setEditJob(final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View editJobView = layoutInflater.inflate(R.layout.edit_job, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(editJobView);

        final EditText edt_d_job_firm = (EditText)editJobView.findViewById(R.id.edt_d_job_firm);
        final EditText edt_d_job_startdate = (EditText)editJobView.findViewById(R.id.edt_d_job_startdate);
        final EditText edt_d_job_enddate = (EditText)editJobView.findViewById(R.id.edt_d_job_enddate);
        final EditText edt_d_job_description = (EditText)editJobView.findViewById(R.id.edt_d_job_desc);

        final Job job = (Job) jobItems.get(position);

        //Lleno los EditText con la info del empleo para ser modificado
        edt_d_job_firm.setText(job.getFirm());
        edt_d_job_startdate.setText(job.getStartdate());
        edt_d_job_enddate.setText(job.getEnddate());
        edt_d_job_description.setText(job.getDescription());

        edt_d_job_startdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Calendar calendar = Calendar.getInstance();

                if(edt_d_job_startdate.getText() != null && edt_d_job_startdate.getText().toString() != null && !edt_d_job_startdate.getText().toString().equals("")){
                    try {
                        calendar.setTime(simpleDateFormat.parse(edt_d_job_startdate.getText().toString()));
                    } catch (ParseException e) {
                    }
                }

                DatePickerDialog dpd = new DatePickerDialog(
                        activity,
                        R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                edt_d_job_startdate.setText(String.format("%02d", dayOfMonth) + "/"
                                        + String.format("%02d", (monthOfYear + 1)) + "/" + year);

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Listo", dpd);
                dpd.show();
            }
        });

        edt_d_job_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Calendar calendar = Calendar.getInstance();

                if (edt_d_job_enddate.getText() != null && edt_d_job_enddate.getText().toString() != null && !edt_d_job_enddate.getText().toString().equals("")) {
                    try {
                        calendar.setTime(simpleDateFormat.parse(edt_d_job_enddate.getText().toString()));
                    } catch (ParseException e) {
                    }
                }

                DatePickerDialog dpd = new DatePickerDialog(
                        activity,
                        R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                edt_d_job_enddate.setText(String.format("%02d", dayOfMonth) + "/"
                                        + String.format("%02d", (monthOfYear + 1)) + "/" + year);

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Listo", dpd);
                dpd.show();
            }
        });

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Editar Empleo")
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if ( !(edt_d_job_startdate.getText().toString().equals("")||
                                        edt_d_job_firm.getText().toString().equals("")||
                                        edt_d_job_description.getText().toString().equals(""))){
                                    Job modifiedJob = new Job();
                                    modifiedJob.setFirm(edt_d_job_firm.getText().toString());
                                    modifiedJob.setStartdate(edt_d_job_startdate.getText().toString());
                                    modifiedJob.setEnddate(edt_d_job_enddate.getText().toString());
                                    modifiedJob.setDescription(edt_d_job_description.getText().toString());
                                    modifiedJob.setId(((Job)jobItems.get(position)).getId());
                                    editJob(job, modifiedJob, position);

                                } else {
                                    Popup.showText(activity, "Todos los campos son obligatorios excepto por la Fecha de Finalizacion", Toast.LENGTH_LONG).show();
                                    setEditJob(position);
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

    public void editJob(final Job job, final Job modifiedJob, final int position){

        SharedPreferences settings = activity.getSharedPreferences(activity.getResources().getString(R.string.prefs_name), 0);
        String username = null;

        username = getUsername();
        final String token = settings.getString("token", null);

        RequestQueue volley = Volley.newRequestQueue(activity);
        String url = activity.getResources().getString(R.string.urlAPI) + "/students/" + username + "/jobs/"+modifiedJob.getId();

        JSONObject jsonParams = new JSONObject();

        try {
            String strStartDate = null;
            String strEndDate = null;

            if(modifiedJob.getStartdate() != null && modifiedJob.getStartdate().toString() != null && !modifiedJob.getStartdate().toString().equals(""))
                strStartDate = modifiedJob.getStartdate().toString();

            if(modifiedJob.getEnddate() != null && modifiedJob.getEnddate().toString() != null && !modifiedJob.getEnddate().toString().equals(""))
                strEndDate = modifiedJob.getEnddate().toString();

            jsonParams.put("company", modifiedJob.getFirm());
            jsonParams.put("position", modifiedJob.getDescription());
            jsonParams.put("dateFrom", strStartDate);
            jsonParams.put("dateTo", strEndDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(activity, "Datos de empleo guardados correctamente", Toast.LENGTH_LONG).show();
                        job.setFirm(modifiedJob.getFirm());
                        job.setStartdate(modifiedJob.getStartdate());
                        job.setEnddate(modifiedJob.getEnddate());
                        job.setDescription(modifiedJob.getDescription());
                        notifyDataSetChanged();
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
                            Popup.showText(activity, JSONBody.getString("message"), Toast.LENGTH_LONG).show();
                            setEditJob(position);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            e.printStackTrace();
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

        volley.add(jsObjRequest);

    }


    private String getToken(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getToken();
    }

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(activity);
        return dataAccess.getUserName();
    }

    public void deleteJob(final int position) {

        final SharedPreferences settings = ((FragmentActivity)activity).getSharedPreferences(activity.getResources().getString(R.string.prefs_name), 0);
        final String username = getUsername();

        Job job = (Job)jobItems.get(position);

        RequestQueue queue = Volley.newRequestQueue(((FragmentActivity)activity));
        String url = activity.getResources().getString(R.string.urlAPI) + "/students/" + username + "/jobs/"+job.getId();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.DELETE,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jobItems.remove(position);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //el response es null cuando borra al compañero,
                        //entonces se borra del listado
                        if (error.networkResponse == null){
                            jobItems.remove(position);
                            notifyDataSetChanged();
                        }

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

}
