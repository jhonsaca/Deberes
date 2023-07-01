package ec.edu.tecnologicoloja.cars;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private Button btn1, btn2, btn3;
    public static final String MyPreferences = "MyPrefs";
    public static final String latitud = "Latitud";
    public static final String longitud = "longitud";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        enableMyLocation();
        enableMyCamera();
        checkCameraHardware(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng miubica = new LatLng(-1.10, -77.57);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(miubica));
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(7);
        mMap.animateCamera(ZoomCam);
        mMap.setOnMapLongClickListener(this);
        //Botón de Localización
        btn1 = (Button) findViewById(R.id.btn1);
        //Botón de Guardar Lugares con el SharedPreferences
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v==btn1){
            miposicion();
        }
        if (v==btn2){
            datos();
        }
        if (v==btn3){
            Intent i = new Intent(MapsActivity.this, MainActivity2.class);
            startActivity(i);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Toast.makeText(MapsActivity.this, "LE DIÓ CLICK AL MAPA", Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "Latitud: "+latLng.latitude+" Longitud: "+latLng.longitude, Toast.LENGTH_LONG).show();
        Marker ubicacion = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación").icon(BitmapDescriptorFactory.fromResource(R.drawable.carrito)));
        //Agregamos un editor de sharedpreferences
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //Guarda los valores del click del mapa
        editor.putFloat(latitud, (float) latLng.latitude);
        editor.putFloat(longitud, (float) latLng.longitude);
        //Enviamos el valor a guardar
        editor.commit();
    }
    private void enableMyLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }

    }
    private void miposicion (){

        LocationManager objLocation = null;
        LocationListener objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new miposicion();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }

        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, objLocListener);

        if(objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            if (miposicion.latitud != 0){
                double lat = miposicion.latitud;
                double lon = miposicion.longitud;
                Toast.makeText(MapsActivity.this, "latitud"+lat+"longitud"+lon, Toast.LENGTH_SHORT).show();
                LatLng miubica = new LatLng(lat, lon);
                Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(miubica).title("Mi ubicación").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miubica));
                CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(18);
                mMap.animateCamera(ZoomCam);
            } else {
                Toast.makeText(MapsActivity.this, "No hay datos", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void datos(){
        double la=sharedpreferences.getFloat(latitud, 0);
        double lo=sharedpreferences.getFloat(longitud,0);
        LatLng ubica = new LatLng(la, lo);
        Marker ubicacion = mMap.addMarker(new MarkerOptions().position(ubica).title("Carrito").icon(BitmapDescriptorFactory.fromResource(R.drawable.carrito)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubica));
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(18);
        mMap.animateCamera(ZoomCam);
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    private void enableMyCamera(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,},1000);

        }

    }
}