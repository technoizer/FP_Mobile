package xyz.coders_note.masjidku;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapMasjid extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat, lng;
    private String nama;
    HashMap<Marker,String> hehe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        nama = b.getString("nama");
        setContentView(R.layout.activity_map_masjid);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng tmp = new LatLng(lat, lng);
        LatLng current;
        GPSTracker mGPS = new GPSTracker(this);
        mGPS.getLocation();
        double currentlat = mGPS.getLatitude();
        double currentlng = mGPS.getLongitude();
        current = new LatLng(currentlat,currentlng);
        mMap.addMarker(new MarkerOptions().position(current).title("Lokasi Anda").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        LatLngBounds.Builder bld = new LatLngBounds.Builder();
        bld.include(tmp);
        bld.include(current);
        LatLngBounds bounds = bld.build();
        mMap.addMarker(new MarkerOptions().position(tmp).title(nama));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,100));

    }
}
