package com.fiubapp.fiubapp;

import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ar.uba.fi.fiubappMobile.utils.Localizador;

public class Principal extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.icon_page);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //viewPager.setOffscreenPageLimit(1);

        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setTypeFace(Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Bold.ttf"));
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: Completar estos m�todos
        /*
        ObtenerPreferenciasConfiguracion();
        if(quiere que lo localicen)
            obtenerLocalizacion();
        */
        obtenerLocalizacion();
    }

    public void obtenerLocalizacion(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Localizador localizador = new Localizador() {
            @Override
            public void cambioUbicacion(LatLng miUbicacion) {
                actualizarLocalizacionAlumno(miUbicacion);
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, localizador.getInstance());
    }

    private void actualizarLocalizacionAlumno(LatLng miUbicacion){
        // TODO: Actualizar la ubicacion en el REST
    }
}