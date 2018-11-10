package com.andradecoder.chat.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andradecoder.chat.R;
import com.andradecoder.chat.modelo.Mensagem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    //SupportMapFragment mapaFragment;
    //MapView mapView;
    //private static final double LATITUDE = -5.825818;
    //private static final double LONGITUDE = -35.234974;
    private static final int REQUEST_FINE_LOCATION = 11;

    private GoogleMap gMap;
    //LOCATION API
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String linkMaps;

    //FirebaseDatabase e DatabaseReference;
    FirebaseDatabase mFirebase;
    DatabaseReference mReference;
    FirebaseAuth mFirebaseAuth;

    String data;
    Date date;

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

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        data = dateFormat.format(date);
        //System.out.println(dateFormat.format(date));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        Button buttoEnviarLocalizacao = view.findViewById(R.id.buttonEnviarLocalizacao);
        buttoEnviarLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TESTE", "Clicou no botão de enviar localização");

                mFirebase = FirebaseDatabase.getInstance();
                mReference = mFirebase.getReference().child("mensagens");
                mFirebaseAuth = FirebaseAuth.getInstance();


                if (ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.i("TESTE", "LATITUDE: " + location.getLatitude() + " LONGITUDE: " + location.getLongitude());
                                linkMaps = "http://www.google.com/maps/place/" + location.getLatitude() + "," + location.getLongitude();
                                Log.i("TESTE", "LINK: " + linkMaps);

                                FirebaseUser user = mFirebaseAuth.getCurrentUser();

                                Mensagem mensagem = new Mensagem(linkMaps, user.getDisplayName(), data);

                                mReference.push().setValue(mensagem);
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_FINE_LOCATION);
                }
            }
        });


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setOnMyLocationButtonClickListener(this);
        gMap.setOnMyLocationClickListener(this);


//        CameraPosition liberty = CameraPosition.builder().target(new LatLng(LATITUDE, LONGITUDE)).zoom(18).bearing(0).build();

//        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
//        gMap.addMarker(new MarkerOptions().position(new LatLng(LATITUDE, LONGITUDE)).title("Título do mapa"));
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LATITUDE, LONGITUDE), 18));
        if (ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        } else {
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

        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), permissoes[0]) == PackageManager.PERMISSION_GRANTED) {
                        gMap.setMyLocationEnabled(true);
                    }
                }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();


    }
}
