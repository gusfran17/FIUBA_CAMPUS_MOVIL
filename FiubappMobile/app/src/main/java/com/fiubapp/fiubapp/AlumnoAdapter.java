package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class AlumnoAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Alumno> alumnoItems;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public AlumnoAdapter(Activity activity, List<Alumno> alumnoItems) {
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

        /*if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);*/
        //ImageView imagen = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView carrera = (TextView) convertView.findViewById(R.id.carrera);

        final Button buttonAdd = (Button) convertView.findViewById(R.id.childButton);
        Alumno a = alumnoItems.get(position);

        // thumbnail image
        //thumbNail.setImageUrl(a.getFoto(), imageLoader);

        nombre.setText(a.getNombre() +" "+ a.getApellido());
        username.setText(a.getUsername());

        if (!a.getCarreras().isEmpty())
            carrera.setText(a.getCarreras().get(0));
        else carrera.setText("");

        if (a.isMyMate()) {
            buttonAdd.setText("Eliminar");
        } else {
            buttonAdd.setText("Agregar");
        }
        return convertView;
    }

}
