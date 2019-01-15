package com.example.wael.mycart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ActivityPannier extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livraison_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createLivreurFragment();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("Map","******************************Start**************************");
        // Add a marker in Sydney and move the camera
        LatLng tunis = new LatLng(36.81, 10.181);
        mMap.addMarker(new MarkerOptions().position(tunis).title("Delivery Location").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tunis));
        Log.d("Map","******************************end**************************");
    }

    public void createLivreurFragment(){
        Log.d("Produit","******************************Start**************************");
        Bundle bundle = new Bundle();
        bundle.putDouble("lat",36.771856);
        bundle.putDouble("lon",10.233533);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = new LivreurFragmenttt().newInstance();
        mFragmentTransaction.add(R.id.livreur_recycler_view, fragment);
        fragment.setArguments(bundle);
        mFragmentTransaction.commit();
        Log.d("Produit","******************************DONE**************************");

    }
}
