package com.example.matt.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import model.Event;
import model.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,View.OnClickListener {
    private GoogleMap gMap;
    private TextView text;
    private Data data = Data.buildData();
    private String currentPerson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_view, container, false);

        text = v.findViewById(R.id.textDescription);

        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonActivity.class);
                intent.putExtra("personID", currentPerson);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMarkerClickListener(this);
        populateMap();
    }

    private void populateMap(){
        List<Event> events = data.getEvents();

        for(int i = 0; i < events.size(); i++){
            Event e = events.get(i);
            LatLng position = new LatLng(e.getLatitude(), e.getLongitude());
            String eventType = e.getEventType();
            Person person = data.getPersonByID(e.getPersonID());
            String name = person.getFirstName() + " " + person.getLastName();

            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            if(eventType.equals("Birth")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            else if(eventType.equals("Baptism")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
            else if(eventType.equals("Marriage")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            else if(eventType.equals("Death")) icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

            Marker marker = gMap.addMarker(new MarkerOptions().position(position).icon(icon).title(eventType + " of " + name));
            marker.setTag(e);

            if(i == 0) {
                onMarkerClick(marker);
                marker.showInfoWindow();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Event event = (Event) marker.getTag();
        currentPerson = event.getPersonID();
        Person person = data.getPersonByID(currentPerson);

        LatLng position = new LatLng(event.getLatitude(),event.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        String name = person.getFirstName() + " " + person.getLastName();
        char gender = Character.toUpperCase(person.getGender());
        String eventType = event.getEventType();
        String location = event.getCity() + ", " + event.getCountry();
        int year = event.getYear();
        String description = name + " (" + gender + ")\n" + eventType + ": " + location + " (" + year + ")";

        text.setText(description);
        return true;
    }

    public void onClick(View v) {
        Intent intent = new Intent(getContext(), PersonActivity.class);
        intent.putExtra("personID", currentPerson);
        startActivity(intent);
    }
}
