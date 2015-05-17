package ar.uba.fi.fiubappMobile.groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiubapp.fiubapp.R;

public class ArchivosTab extends Fragment {

    private String urlAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlAPI = getResources().getString(R.string.urlAPI);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View regularSearchView = inflater.inflate(R.layout.archivos_grupo_tab, container, false);
        return regularSearchView;
    }

    public static ArchivosTab nuevoGrupo(int idGrupo) {
        ArchivosTab tab = new ArchivosTab();
        Bundle args = new Bundle();
        args.putInt("idGrupo",idGrupo);
        tab.setArguments(args);
        return tab;
    }
}
