package ar.uba.fi.fiubappMobile.partners;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class StudentSearchTabsAdapter  extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    private static final String[] CONTENT = new String[] { "Simple", "Avanzada" };

    public StudentSearchTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RegularSearch regularSearchTab = new RegularSearch();
                return regularSearchTab;
            case 1:
                AdvancedSearch advancedSearchTab = new AdvancedSearch();
                return advancedSearchTab;
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
