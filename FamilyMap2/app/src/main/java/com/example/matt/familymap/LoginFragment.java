package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LoginFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.login_view, container, false);
        Button SignInButton = (Button) v.findViewById(R.id.buttonLogin);
        Button RegisterButton = (Button) v.findViewById(R.id.buttonRegister);
        SignInButton.setOnClickListener(this);
        RegisterButton.setOnClickListener(this);

        return v;
    }

    private void attemptLogin(){
        Toast.makeText(getContext(),"Login", Toast.LENGTH_LONG).show();
    }

    private void attemptRegister(){
        Toast.makeText(getContext(),"Register", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                attemptLogin();
                break;
            case R.id.buttonRegister:
                attemptRegister();
                break;
        }
    }
}
