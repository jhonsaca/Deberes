package ec.edu.tecnologicoloja.gpsmarker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap Map;
    private Button btn1;
    //private TextView txt1, txt2;
    double lat = 0, lon = 0;
    private LatLng miubica;
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableMyLocation();
        btn1 = (Button) findViewById(R.id.button);
        //txt1 = (TextView) findViewById(R.id.txtal);
        //txt2 = (TextView) findViewById(R.id.txtlo);
        btn1.setOnClickListener(this::getUbicacion);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

    }
    public void getUbicacion(View v){
        if (v==btn1){
            posicion();
            mapFragment.getMapAsync(this);
        }
    }
    public void posicion(){
        LocationManager objLocation = null;
        LocationListener objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new myposicion();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }

        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, objLocListener);

        if(objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            if (myposicion.latitud != 0){
                lat = myposicion.latitud;
                lon = myposicion.longitud;
                Log.i(null, "posicion latitud: "+lat);
                Log.i(null, "posicion longitud: "+lon);
                //txt1.setText("Su Latitud es: "+lat);
                //txt2.setText("Su Longitud es: "+lon);
            }

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Map = googleMap;
        Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Map.getUiSettings().setZoomControlsEnabled(true);
        Map.getUiSettings().setMyLocationButtonEnabled(true);
        miubica = new LatLng(lat, lon);
        Map.moveCamera(CameraUpdateFactory.newLatLng(miubica));
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(15);
        Map.animateCamera(ZoomCam);
        Map.addMarker(new MarkerOptions().position(miubica).title("Su ubicación"));
    }

    private void enableMyLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }


    }
}