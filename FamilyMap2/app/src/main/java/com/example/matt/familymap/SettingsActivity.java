package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.matt.familymap.tool.Data;
import com.example.matt.familymap.Task.GetFamilyEventsTask;
import com.example.matt.familymap.Task.GetFamilyTask;

import java.util.zip.Inflater;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView resync;
    private Data data;
    private GetFamilyTask getFamilyTask;
    private GetFamilyEventsTask getFamilyEventsTask;
    private static SettingsActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        activity = this;

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        data = Data.buildData();

        resync = findViewById(R.id.view_resync);
        resync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFamilyTask = new GetFamilyTask(data.getAuthToken(), data.getServer(), getApplicationContext());
                getFamilyTask.execute();

                getFamilyEventsTask = new GetFamilyEventsTask(data.getAuthToken(), data.getServer(), getApplicationContext());
                getFamilyEventsTask.execute();
            }
        });

    }

    public void drawMap(){
        MapFragment map = MapFragment.getMapFragment();
        View view = map.getView();
        TextView description = view.findViewById(R.id.textDescription);
        description.setText("");
        map.populateMap();
        finish();
    }

    public static SettingsActivity getActivity(){
        return activity;
    }

    @Override
    public void onClick(View v) {

    }
}
