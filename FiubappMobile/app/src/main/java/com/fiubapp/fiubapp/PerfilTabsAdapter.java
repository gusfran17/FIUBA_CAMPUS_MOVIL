package com.fiubapp.fiubapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PerfilTabsAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 5;

    private static final String[] CONTENT = new String[] { "Datos", "FIUBA", "Empleos", "Educacion", "Apuntes" };

    public PerfilTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Perfil_personal tabPersonal = new Perfil_personal();
                return tabPersonal;

            case 1:
                Perfil_fiuba tabFiuba = new Perfil_fiuba();
                return tabFiuba;

            case 2:
                Perfil_empleo tabEmpleo = new Perfil_empleo();
                return tabEmpleo;

            case 3:
                Perfil_secundaria tabSecundaria = new Perfil_secundaria();
                return tabSecundaria;

            case 4:
                Perfil_apuntes tabApuntes = new Perfil_apuntes();
                return tabApuntes;
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