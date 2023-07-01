package ec.edu.tecnologicoloja.cars;

import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class miposicion implements LocationListener {
    public static double latitud;
    public static double longitud;
    public static boolean statusGPS;
    public static Location coordenadas;

    @Override
    public void onLocationChanged(@NonNull Location location) {

        latitud=location.getLatitude();
        longitud=location.getLongitude();
        coordenadas=location;

    }

    @Override
    public void onProviderEnabled (@NonNull String provider) {
        statusGPS=true;
    }

    @Override
    public void onProviderDisabled (@NonNull String provider) {
        statusGPS=false;
    }
}
