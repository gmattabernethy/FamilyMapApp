package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import model.Event;
import model.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap gMap;
    private Data data = Data.buildData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.map_view, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println();
        gMap = googleMap;
        populateMap();

        Event event = data.getEvents().get(0);
        LatLng position = new LatLng(event.getLongitude(),event.getLatitude());

        gMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    private void populateMap(){
        List<Event> events = data.getEvents();

        for(Event e:events){
            LatLng position = new LatLng(e.getLongitude(),e.getLatitude());
            String eventType = e.getEventType();
            Person person = data.getPerson(e.getPersonID());
            String name = person.getFirstName() + " " + person.getLastName();

            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            if(eventType.equals("Birth")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            else if(eventType.equals("Baptism")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
            else if(eventType.equals("Marriage")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            else if(eventType.equals("Death")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            gMap.addMarker(new MarkerOptions().position(position).icon(icon).title(eventType + " of " + name));
        }
    }
}
