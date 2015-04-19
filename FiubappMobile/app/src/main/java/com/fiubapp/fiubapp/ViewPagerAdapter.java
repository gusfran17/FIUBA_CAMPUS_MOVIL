package com.fiubapp.fiubapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter{

    final int PAGE_COUNT = 5;

    private static final String[] CONTENT = new String[] { "Muro", "Compañeros", "Grupos", "Notificacion", "Config" };

    private static final int[] ICONS = new int[] {
            R.drawable.tab_muro_icon,
            R.drawable.tab_companeros_icon,
            R.drawable.tab_grupos_icon,
            R.drawable.tab_notificacion_icon,
            R.drawable.tab_perfil_icon
    };

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                FragmentTab1 fragmenttab1 = new FragmentTab1();
                return fragmenttab1;

            case 1:
                FragmentTab2 fragmenttab2 = new FragmentTab2();
                return fragmenttab2;

            case 2:
                FragmentTab3 fragmenttab3 = new FragmentTab3();
                return fragmenttab3;

            case 3:
                FragmentTab3 fragmenttab4 = new FragmentTab3();
                return fragmenttab4;

            case 4:
                Config config = new Config();
                return config;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override public int getIconResId(int index) {
        return ICONS[index];
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }
}