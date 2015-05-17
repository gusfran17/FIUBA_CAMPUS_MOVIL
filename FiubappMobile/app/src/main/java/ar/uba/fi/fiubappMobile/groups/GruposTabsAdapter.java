package ar.uba.fi.fiubappMobile.groups;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fiubapp.fiubapp.R;

public class GruposTabsAdapter extends FragmentPagerAdapter {

    private String[] CONTENT = null;

    public GruposTabsAdapter(FragmentManager fm, Context context) {

        super(fm);

        CONTENT = new String[] {
                context.getResources().getString(R.string.informacion),
                context.getResources().getString(R.string.discusiones),
                context.getResources().getString(R.string.archivos)};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InformacionTab();
            case 1:
                return new DiscusionesTab();
            case 2:
                return new ArchivosTab();
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
