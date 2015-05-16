package com.fiubapp.fiubapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PerfilTabsCompaneroAdapter extends FragmentPagerAdapter{

    private Alumno companero;

    private String[] TABS = null;

    public PerfilTabsCompaneroAdapter(FragmentManager fm, Alumno companero) {
        super(fm);
        this.companero = companero;

        if(this.companero.isMyMate())
            TABS = new String[] { "Muro", "Datos", "FIUBA", "Empleos", "Educacion", "Apuntes" };
        else
            TABS = new String[] { "Datos", "FIUBA" };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                if(this.companero.isMyMate())
                    return new Perfil_apuntes();
                else
                    return Perfil_personal.newContact(companero);
            case 1:
                if(this.companero.isMyMate())
                    return Perfil_personal.newContact(companero);
                else
                    return Perfil_fiuba.newContact(companero);
            case 2:
                if(this.companero.isMyMate())
                    return Perfil_fiuba.newContact(companero);
                else
                    return Perfil_empleo.newContact(companero);
            case 3:
                if(this.companero.isMyMate())
                    return Perfil_empleo.newContact(companero);
                else
                    return Perfil_secundaria.newContact(companero);
            case 4:
                if(this.companero.isMyMate())
                    return new Perfil_secundaria();
                else
                    return new Perfil_apuntes();
            case 5:
                return new Perfil_apuntes();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TABS[position % TABS.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}