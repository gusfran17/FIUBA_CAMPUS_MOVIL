package ar.uba.fi.fiubappMobile.groups;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fiubapp.fiubapp.R;

public class GruposTabsAdapter extends FragmentPagerAdapter {

    private int idGrupo = -1;
    private String[] CONTENT = null;

    public GruposTabsAdapter(FragmentManager fm, Context context, int idGrupo, boolean esMiembro) {

        super(fm);

        if(esMiembro)
            CONTENT = new String[] {
                    context.getResources().getString(R.string.informacion),
                    context.getResources().getString(R.string.discusiones),
                    context.getResources().getString(R.string.archivos)};
        else
            CONTENT = new String[] {
                    context.getResources().getString(R.string.informacion)};

        this.idGrupo = idGrupo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InformacionTab.nuevoGrupo(idGrupo);
            case 1:
                return DiscusionesTab.nuevoGrupo(idGrupo);
            case 2:
                return ArchivosTab.nuevoGrupo(idGrupo);
        }

        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }
}
