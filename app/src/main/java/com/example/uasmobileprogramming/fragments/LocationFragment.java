package com.example.uasmobileprogramming.fragments;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uasmobileprogramming.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    private final LatLng alphaLocation = new LatLng(-6.193924061113853, 106.78813220277623);
    private final LatLng betaLocation = new LatLng(-6.20175020412279, 106.78223868546155);
    private Marker activeMarker = null;

    private Button alphaBtn, betaBtn;

    private static final int DEFAULT_ZOOM = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        alphaBtn = v.findViewById(R.id.alpha_btn);
        betaBtn = v.findViewById(R.id.beta_btn);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        setMarkerPosition(alphaLocation, "Cinema CGP Alpha");
        alphaBtn.setOnClickListener(view -> setMarkerPosition(alphaLocation, "Cinema CGP Alpha"));
        betaBtn.setOnClickListener(view -> setMarkerPosition(betaLocation, "Cinema CGP Beta"));
    }

    public void setMarkerPosition(LatLng loc, String title){
        if(activeMarker != null) activeMarker.remove();
        activeMarker = mMap.addMarker(new MarkerOptions()
                        .position(loc)
                        .title(title)
                    );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, DEFAULT_ZOOM));
    }
}