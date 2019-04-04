package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matt.familymap.tool.Data;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap gMap;
    private TextView text;
    private Data data = Data.buildData(getContext());
    private String currentPerson;
    private String eventID;
    private static MapFragment mapFragment;
    private List<Marker> markers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_view, container, false);
        markers = new ArrayList<>();
        mapFragment = this;
        text = v.findViewById(R.id.textDescription);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventID = null;
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMarkerClickListener(this);
        populateMap();
    }

    public void populateMap(){

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    public void onClick(View v) {

    }
}
