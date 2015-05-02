package ar.uba.fi.fiubappMobile.Jobs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fiubapp.fiubapp.Perfil_empleo;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import org.json.JSONObject;

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

        TextView job_firm = (TextView) convertView.findViewById(R.id.txt_job_header);
        TextView  job_startdate = (TextView) convertView.findViewById(R.id.edt_job_startdate);
        TextView job_enddate = (TextView) convertView.findViewById(R.id.edt_job_enddate);
        TextView job_description = (TextView) convertView.findViewById(R.id.edt_job_desc);

        ImageView img_delete_job = (ImageView) convertView.findViewById(R.id.img_delete_job);
        ImageView img_edit_job = (ImageView) convertView.findViewById(R.id.img_edit_job);

        img_delete_job.setOnClickListener(new imageDeleteViewClickListener(position));
        img_edit_job.setOnClickListener(new imageEditViewClickListener(position));
        Job job = jobItems.get(position);


        job_firm.setText(job.getFirm());
        job_startdate.setText(job.getStartdate());
        job_enddate.setText(job.getEnddate());
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

            }
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

    public void deleteJob(final int position) {

        final SharedPreferences settings = ((FragmentActivity)activity).getSharedPreferences(activity.getResources().getString(R.string.prefs_name), 0);
        final String username = getUsername();

        Job job = (Job)jobItems.get(position);

        RequestQueue queue = Volley.newRequestQueue(((FragmentActivity)activity));
        String url = R.string.urlAPI + "/students/" + username + "/jobs/"+job.getId();

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
