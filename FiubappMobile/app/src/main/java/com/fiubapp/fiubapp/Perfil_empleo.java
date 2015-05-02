package com.fiubapp.fiubapp;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ar.uba.fi.fiubappMobile.Jobs.Job;

public class Perfil_empleo extends Fragment {
    private View view;
    private EditText edt_d_job_firm;
    private EditText edt_d_job_startdate;
    private EditText edt_d_job_enddate;
    private EditText edt_d_job_description;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.perfil_empleos, container, false);
        final ImageView imgAddJob = (ImageView)this.view.findViewById(R.id.img_add_job);

        imgAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCrear();
            }
        });

        return view;
    }

    private void setCrear() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View editJobView = layoutInflater.inflate(R.layout.edit_job, null);
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(editJobView);

        edt_d_job_firm = (EditText)editJobView.findViewById(R.id.edt_d_job_firm);
        edt_d_job_startdate = (EditText)editJobView.findViewById(R.id.edt_d_job_startdate);
        edt_d_job_enddate = (EditText)editJobView.findViewById(R.id.edt_d_job_enddate);
        edt_d_job_description = (EditText)editJobView.findViewById(R.id.edt_d_job_desc);

        edt_d_job_startdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                edt_d_job_startdate = (EditText)v.findViewById(R.id.edt_d_job_startdate);
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

                 edt_d_job_enddate = (EditText) v.findViewById(R.id.edt_d_job_enddate);
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

                                createJob(job);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }




    private void createJob(Job job) {
    }
}