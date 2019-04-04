package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_view, container, false);

        Button mapButton = (Button) v.findViewById(R.id.buttonToMap);
        mapButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonToMap:
                toMap();
                break;
        }
    }

    public void toMap(){
        //TODO processing wheel
        MainActivity activity = (MainActivity) getActivity();
        activity.openMap();
    }
}
