package ar.uba.fi.fiubappMobile.Jobs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiubapp.fiubapp.R;

import java.util.List;

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
            convertView = inflater.inflate(R.layout.list_row, null);

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
                //carreraItems.remove(position);
                //perfilFiuba.eliminarCarrera(position);
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
                //carreraItems.remove(position);
                //perfilFiuba.eliminarCarrera(position);
            }
        }
    }


}
