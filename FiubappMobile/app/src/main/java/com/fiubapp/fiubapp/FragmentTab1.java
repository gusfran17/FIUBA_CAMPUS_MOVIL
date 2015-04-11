package com.fiubapp.fiubapp;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FragmentTab1 extends Fragment {
    ListView lv1;
    private TextView text;
    private static final String TAG = FragmentTab1.class.getSimpleName();

    private ProgressDialog pDialog;
    private List<Alumno> alumnoList = new ArrayList<Alumno>();
    private ListView listView;
    private AlumnoAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmenttab1, container, false);
        return view;
    }
}