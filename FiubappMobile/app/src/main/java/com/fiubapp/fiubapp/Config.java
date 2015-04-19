package com.fiubapp.fiubapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Config extends Fragment {

    private String urlAPI="";

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

        View view = inflater.inflate(R.layout.config, container, false);

        ListView list = (ListView)view.findViewById(R.id.listconfig);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        Intent i = new Intent(getActivity(), PerfilTabs.class);
                        startActivity(i);
                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                }
            }
        });

        return view;
    }

}
