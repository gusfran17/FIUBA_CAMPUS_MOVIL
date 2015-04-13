package com.fiubapp.fiubapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerfilTab extends Fragment {

    private static final String TAG = FragmentTab2.class.getSimpleName();
    private String urlAPI="";
    private TextView perfil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isAdded()){
            urlAPI = getResources().getString(R.string.urlAPI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perfiltab, container, false);
        perfil = (TextView)view.findViewById(R.id.verperfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Perfil.class);
                startActivity(i);
            }
        });
        return view;
    }

}
