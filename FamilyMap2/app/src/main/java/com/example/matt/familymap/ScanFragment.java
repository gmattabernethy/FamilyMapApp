package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScanFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scan_view, container, false);

        Button scanButton = (Button) v.findViewById(R.id.buttonScan);
        scanButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonScan:
                scan();
                break;
        }
    }

    public void scan(){
        //TODO processing wheel
        MainActivity activity = (MainActivity) getActivity();
        activity.openList();
    }
}
