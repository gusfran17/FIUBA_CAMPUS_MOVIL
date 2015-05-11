package ar.uba.fi.fiubappMobile.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public abstract class Localizador {

    private static LocationListener instance = null;
    private static LatLng miUbicacion = new LatLng(-34.61775789, -58.36797953);

    protected Localizador() {
    }

    public LocationListener getInstance() {
        if(instance == null) {
            instance = getLocationListener();
        }
        return instance;
    }

    public LatLng getMiUbicacion(){
        return miUbicacion;
    }

    private LocationListener getLocationListener(){
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                cambioUbicacion(miUbicacion);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        return locationListener;
    }

    public abstract void cambioUbicacion(LatLng miUbicacion);
}
