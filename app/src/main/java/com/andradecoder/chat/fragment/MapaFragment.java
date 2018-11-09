package com.andradecoder.chat.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andradecoder.chat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback{

    //SupportMapFragment mapaFragment;
    MapView mapView;
    private static final double LATITUDE = -5.825818;
    private static final double LONGITUDE = -35.234974;
    private static final int REQUEST_FINE_LOCATION = 11;

    private GoogleMap gMap;

    final String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);


//        mapView = view.findViewById(R.id.mapView);
//
//        if(mapView != null){
//            mapView.onCreate(null);
//            mapView.onResume();
//            mapView.getMapAsync(this);
//        }


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);



        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        CameraPosition liberty = CameraPosition.builder().target(new LatLng(LATITUDE, LONGITUDE)).zoom(18).bearing(0).build();

//        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
//        gMap.addMarker(new MarkerOptions().position(new LatLng(LATITUDE, LONGITUDE)).title("Título do mapa"));
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LATITUDE, LONGITUDE), 18));
        if(ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED){
            gMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }


        //gMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        if(ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED){
//            gMap.setMyLocationEnabled(true);
//        }else{
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_FINE_LOCATION);
//        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED){
                        gMap.setMyLocationEnabled(true);
                    }
                }
        }
    }
}
