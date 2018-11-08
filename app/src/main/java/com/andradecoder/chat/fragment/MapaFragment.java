package com.andradecoder.chat.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andradecoder.chat.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback{

    SupportMapFragment mapaFragment;
    private GoogleMap mMap;

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        mapaFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapaFragment.getMapAsync(this);

        if(mapaFragment == null){

        }

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mapaFragment.addMarker(new MarkerOptions().position(new LatLng(-25.443150, -49.238243)).title("Jardim Botânico"));
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
