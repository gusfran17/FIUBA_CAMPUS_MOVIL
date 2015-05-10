package com.fiubapp.fiubapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiubapp.fiubapp.dominio.Carrera;

import java.util.HashMap;
import java.util.List;

public class CareerExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Carrera> listCareer; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listSubject;
    private Perfil_fiuba perfilFiuba;

    public CareerExpandableAdapter(Perfil_fiuba perfilFiuba,Context context, List<Carrera> listCareer,HashMap<String, List<String>> listSubject) {
        this.context = context;
        this.listCareer = listCareer;
        this.listSubject = listSubject;
        this.perfilFiuba = perfilFiuba;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
         Carrera career = this.listCareer.get(groupPosition);
        return this.listSubject.get(career.getCodigo()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.career_list_row_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lbl_subject_name);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Carrera career = this.listCareer.get(groupPosition);
        return this.listSubject.get(career.getCodigo()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listCareer.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listCareer.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Carrera career = (Carrera) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.career_list_row_group, null);
        }

        TextView lblCareer = (TextView) convertView.findViewById(R.id.lbl_career_name);
        lblCareer.setTypeface(null, Typeface.BOLD);
        lblCareer.setText(career.getNombre());

        ImageView imgBorrarCarrera = (ImageView) convertView.findViewById(R.id.img_delete_career);
        Carrera selectedCareer = listCareer.get(groupPosition);
        if(listCareer.size() == 1 || !selectedCareer.getSePuedeEliminar())
            imgBorrarCarrera.setVisibility(View.INVISIBLE);
        else {
            imgBorrarCarrera.setVisibility(View.VISIBLE);
            imgBorrarCarrera.setOnClickListener(new imageViewClickListener(groupPosition));
        }

        TextView lblProgressDesc = (TextView) convertView.findViewById(R.id.lbl_progress_description);
        ProgressBar careerProgress = (ProgressBar) convertView.findViewById(R.id.career_progressBar);
        //Si no hay resultados que mostrar o el grupo no esta expandido no muestro nada
        if ((career.getTotalAmount()== 0) || (!isExpanded)){
            lblProgressDesc.setVisibility(View.GONE);
            careerProgress.setVisibility(View.GONE);
        } else {
            lblProgressDesc.setText("Usted tiene un total de "+career.getCurrentAmount() + " de " + career.getTotalAmount() + " créditos");
            int progress = (career.getCurrentAmount()*100)/career.getTotalAmount();
            careerProgress.setProgress(progress);


            if (perfilFiuba.getArguments() != null) {

                if (perfilFiuba.getArguments().getBoolean("isMyMate")) {
                        lblProgressDesc.setVisibility(View.VISIBLE);
                        careerProgress.setVisibility(View.VISIBLE);
                }else{
                    lblProgressDesc.setVisibility(View.GONE);
                    careerProgress.setVisibility(View.GONE);
                }
            } else {
                    lblProgressDesc.setVisibility(View.VISIBLE);
                    careerProgress.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }



    class imageViewClickListener implements View.OnClickListener {
        int position;

        public imageViewClickListener(int pos) {
            this.position = pos;
        }

        public void onClick(View v) {
            {
                //carreraItems.remove(position);
                perfilFiuba.eliminarCarrera(position);
            }
        }
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
