package com.fiubapp.fiubapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class Perfil extends Activity{

    private int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
    }

    public void onClickImgEditar(View v) {
        final ImageView imgEditar = (ImageView)findViewById(R.id.imgEditar);

        final EditText fechaInicio = (EditText)findViewById(R.id.etFechaInicio);
        final EditText fechaFin = (EditText)findViewById(R.id.etFechaFin);
        final EditText titulo = (EditText)findViewById(R.id.etTitulo);
        final EditText escuela = (EditText)findViewById(R.id.etEscuela);

        fechaInicio.setEnabled(!fechaInicio.isEnabled());
        fechaFin.setEnabled(!fechaFin.isEnabled());
        titulo.setEnabled(!titulo.isEnabled());
        escuela.setEnabled(!escuela.isEnabled());

        if(titulo.isEnabled())
            imgEditar.setImageResource(R.drawable.ic_editar_selected);
        else
            imgEditar.setImageResource(R.drawable.ic_editar);
    }

    public void onClickFechaInicio(View v) {

        final EditText fechaInicio = (EditText)findViewById(R.id.etFechaInicio);

        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fechaInicio.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void onClickFechaFin(View v) {

        final EditText fechaFin = (EditText)findViewById(R.id.etFechaFin);

        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fechaFin.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

}
