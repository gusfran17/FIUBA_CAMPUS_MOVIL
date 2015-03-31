package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Alumno> alumnoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Alumno> alumnoItems) {
        this.activity = activity;
        this.alumnoItems = alumnoItems;
    }

    @Override
    public int getCount() {
        return alumnoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return alumnoItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView padron = (TextView) convertView.findViewById(R.id.padron);
        TextView carrera = (TextView) convertView.findViewById(R.id.carrera);

        Alumno a = alumnoItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(a.getFoto(), imageLoader);

        nombre.setText(a.getNombre());

        padron.setText(String.valueOf(a.getPadron()));

        // genre
        /*String carreraStr = "";
        for (byte str : a.getCarreras()) {
            carreraStr += Byte.toString(str) + ", ";
        }
        carreraStr = carreraStr.length() > 0 ? carreraStr.substring(0,
                carreraStr.length() - 2) : carreraStr;
        carrera.setText(carreraStr);*/

        return convertView;
    }

}
