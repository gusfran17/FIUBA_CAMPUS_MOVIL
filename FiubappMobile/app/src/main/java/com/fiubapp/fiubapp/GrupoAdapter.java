package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fiubapp.fiubapp.dominio.Grupo;
import java.util.List;

public class GrupoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Grupo> grupoItems;

    public GrupoAdapter(Activity activity, List<Grupo> grupoItems) {
        this.activity = activity;
        this.grupoItems = grupoItems;
    }

    @Override
    public int getCount() {
        return grupoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return grupoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View grupoFilaView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (grupoFilaView == null)
            grupoFilaView = inflater.inflate(R.layout.grupo_fila, null);

        TextView nombre = (TextView) grupoFilaView.findViewById(R.id.nombre_grupo);
        TextView cantidadGrupo = (TextView) grupoFilaView.findViewById(R.id.cantidad_grupo);

        Grupo grupoSeleccionado = grupoItems.get(position);
        nombre.setText(grupoSeleccionado.getNombre());

        // Por ahora nomas!!
        int numAleatorio= (int)Math.floor(Math.random()*(100));
        cantidadGrupo.setText(numAleatorio + " miembros");

        //imgBorrarCarrera.setOnClickListener(new imageViewClickListener(position));
        return grupoFilaView;
    }

    class imageViewClickListener implements View.OnClickListener {
        int position;

        public imageViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {

            }
        }
    }
}
