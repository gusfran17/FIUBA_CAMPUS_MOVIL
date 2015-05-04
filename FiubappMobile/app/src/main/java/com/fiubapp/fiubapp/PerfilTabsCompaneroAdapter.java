package com.fiubapp.fiubapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PerfilTabsCompaneroAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 5;

    private Alumno companero;

    private static final String[] CONTENT = new String[] { "Datos", "FIUBA", "Empleos", "Educacion", "Apuntes" };

    public PerfilTabsCompaneroAdapter(FragmentManager fm, Alumno companero) {
        super(fm);
        this.companero = companero;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Perfil_personal tabPersonal = Perfil_personal.newContact(companero);
                return tabPersonal;

            case 1:
                Perfil_fiuba tabFiuba = Perfil_fiuba.newContact(companero);
                return tabFiuba;

            case 2:
                Perfil_empleo tabEmpleo = Perfil_empleo.newContact(companero);
                return tabEmpleo;

            case 3:
                Perfil_secundaria tabSecundaria = Perfil_secundaria.newContact(companero);
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