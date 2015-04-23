package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fiubapp.fiubapp.dominio.Carrera;

import java.util.List;

public class CarreraAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Carrera> carreraItems;

    public CarreraAdapter(Activity activity, List<Carrera> carreraItems) {
        this.activity = activity;
        this.carreraItems = carreraItems;
    }

    @Override
    public int getCount() {
        return carreraItems.size();
    }

    @Override
    public Object getItem(int location) {
        return carreraItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View carreraFilaView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (carreraFilaView == null)
            carreraFilaView = inflater.inflate(R.layout.carrera_fila, null);

        TextView nombre = (TextView) carreraFilaView.findViewById(R.id.nombre_carrera);
        ImageView imgBorrarCarrera = (ImageView) carreraFilaView.findViewById(R.id.imgBorrarCarrera);

        Carrera carreraSeleccionada = carreraItems.get(position);
        nombre.setText(carreraSeleccionada.getNombre());

        imgBorrarCarrera.setOnClickListener(new imageViewClickListener(position));
        return carreraFilaView;
    }

    class imageViewClickListener implements View.OnClickListener {
        int position;

        public imageViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {
                carreraItems.remove(position);
                //((PerfilTabs)activity).eliminarCarrera(position);
            }
        }
    }
}
