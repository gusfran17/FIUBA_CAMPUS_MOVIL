package com.fiubapp.fiubapp;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.fiubappMobile.Jobs.Job;
import ar.uba.fi.fiubappMobile.Jobs.JobAdapter;
import ar.uba.fi.fiubappMobile.utils.DataAccess;

public class Perfil_empleo extends Fragment {
    private static final String TAG = Perfil_empleo.class.getSimpleName();
    private View view;
    private String firm;
    private String startDate;
    private String endDate;
    private String description;
    private JobAdapter jobAdapter;
    private List<Job> jobsList = new ArrayList<Job>();
    private ListView jobsListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.perfil_empleos, container, false);
        final ImageView imgAddJob = (ImageView)this.view.findViewById(R.id.img_add_job);
        RelativeLayout header = (RelativeLayout) this.view.findViewById(R.id.header);

        imgAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateJob(null);
            }
        });

        jobsListView = (ListView) this.view.findViewById(R.id.list_jobs);
        jobAdapter = new JobAdapter(getActivity(), jobsList);
        jobsListView.setAdapter(jobAdapter);
        if (getArguments() != null) {
            imgAddJob.setVisibility(View.INVISIBLE);
            if (getArguments().getBoolean("isMyMate")){
                fillJobsList(getArguments().getString("userName"));
            } else {
                    header.setVisibility(View.INVISIBLE);
            }

        } else {
            fillJobsList(getUsername());
        }

        return view;
    }


    private void setCreateJob(Job job) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View editJobView = layoutInflater.inflate(R.layout.edit_job, null);
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(editJobView);

        final EditText edt_d_job_firm = (EditText)editJobView.findViewById(R.id.edt_d_job_firm);
        final EditText edt_d_job_startdate = (EditText)editJobView.findViewById(R.id.edt_d_job_startdate);
        final EditText edt_d_job_enddate = (EditText)editJobView.findViewById(R.id.edt_d_job_enddate);
        final EditText edt_d_job_description = (EditText)editJobView.findViewById(R.id.edt_d_job_desc);

        if (job!=null){
            edt_d_job_firm.setText(job.getFirm());
            edt_d_job_startdate.setText(job.getStartdate());
            edt_d_job_enddate.setText(job.getStartdate());
            edt_d_job_description.setText(job.getDescription());
        }

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
                        getActivity(),
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
                         getActivity(),
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
                .setTitle("Crear Empleo")
                .setPositiveButton("Crear",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Job job = new Job();
                                job.setFirm(edt_d_job_firm.getText().toString());
                                job.setStartdate(edt_d_job_startdate.getText().toString());
                                job.setEnddate(edt_d_job_enddate.getText().toString());
                                job.setDescription(edt_d_job_description.getText().toString());
                                job.setCanBeRemoved(true);
                                if (!(edt_d_job_startdate.getText().toString().equals("") ||
                                        edt_d_job_firm.getText().toString().equals("") ||
                                        edt_d_job_description.getText().toString().equals(""))) {

                                    createJob(job);

                                } else {
                                    Popup.showText(getActivity(), "Todos los campos son obligatorios excepto por la Fecha de Finalización", Toast.LENGTH_LONG).show();
                                    setCreateJob(job);
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


    private void createJob(final Job job) {
        SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        String username = null;
        username = getUsername();
        final String token = settings.getString("token", null);

        RequestQueue volley = Volley.newRequestQueue(getActivity());
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/jobs";

        JSONObject jsonParams = new JSONObject();

        try {
            String strStartDate = null;
            String strEndDate = null;

            if(job.getStartdate() != null && job.getStartdate().toString() != null && !job.getStartdate().toString().equals(""))
                strStartDate = job.getStartdate().toString();

            if(job.getEnddate() != null && job.getEnddate().toString() != null && !job.getEnddate().toString().equals(""))
                strEndDate = job.getEnddate().toString();

            jsonParams.put("company", job.getFirm());
            jsonParams.put("position", job.getDescription());
            jsonParams.put("dateFrom", strStartDate);
            jsonParams.put("dateTo", strEndDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Popup.showText(getActivity(), "Datos de empleo guardados correctamente", Toast.LENGTH_LONG).show();
                        try {
                            job.setId(response.getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jobsList.add(job);
                        jobAdapter.notifyDataSetChanged();
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
                            setCreateJob(job);

                        } catch (JSONException e) {

                        } catch (UnsupportedEncodingException e) {

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

    private void fillJobsList(final String username) {
        SharedPreferences settings = getActivity().getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        final String token = settings.getString("token", null);
        String url = this.getString(R.string.urlAPI) + "/students/" + username + "/jobs";

        jobsList.clear();
        // Creating volley request obj

        JsonArrayRequest studentReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Job job = new Job();
                                job.setFirm(obj.getString("company"));
                                job.setStartdate(obj.getString("dateFrom").replace("null", ""));
                                job.setEnddate(obj.getString("dateTo").replace("null", ""));
                                job.setDescription(obj.getString("position"));
                                job.setId(obj.getInt("id"));
                                job.setCanBeRemoved(username.equals(getUsername()));
                                jobsList.add(job);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        jobAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;

            }
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(studentReq);

    }


    public static Perfil_empleo newContact(Alumno companero) {

        Perfil_empleo perfil = new Perfil_empleo();

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

    private String getUsername(){
        DataAccess dataAccess = new DataAccess(getActivity());
        return dataAccess.getUserName();
    }

}