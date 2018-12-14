package com.example.matt.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;

import com.example.matt.familymap.Task.GetFamilyEventsTask;
import com.example.matt.familymap.Task.GetFamilyTask;
import com.example.matt.familymap.Task.LoginTask;
import com.example.matt.familymap.Task.RegisterTask;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText hostView;
    private EditText portView;
    private EditText usernameView;
    private EditText passwordView;
    private EditText fNameView;
    private EditText lNameView;
    private EditText emailView;
    private RadioGroup genderGroup;
    private RequestQueue queue;
    private Button loginButton;
    private Button registerButton;

    private LoginTask loginTask = null;
    private RegisterTask registerTask = null;
    private GetFamilyTask getFamilyTask = null;
    private GetFamilyEventsTask getFamilyEventsTask = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.login_view, container, false);
        Button SignInButton = (Button) v.findViewById(R.id.buttonLogin);
        Button RegisterButton = (Button) v.findViewById(R.id.buttonRegister);
        SignInButton.setOnClickListener(this);
        RegisterButton.setOnClickListener(this);

        //queue = API.getInstance(getContext()).getRequestQueue();

        hostView = (EditText) v.findViewById(R.id.editHost);
        portView = (EditText) v.findViewById(R.id.editPort);
        usernameView = (EditText) v.findViewById(R.id.editUserName);
        passwordView = (EditText) v.findViewById(R.id.editPassword);
        fNameView = (EditText) v.findViewById(R.id.editFName);
        lNameView = (EditText) v.findViewById(R.id.editLName);
        emailView = (EditText) v.findViewById(R.id.editEmail);
        genderGroup = (RadioGroup) v.findViewById(R.id.groupGender);
        loginButton = (Button) v.findViewById(R.id.buttonLogin);
        registerButton = (Button) v.findViewById(R.id.buttonRegister);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(hostView.getText().length() == 0 || portView.getText().length() == 0 || usernameView.getText().length() == 0
                        || passwordView.getText().length() == 0 || fNameView.getText().length() == 0 || lNameView.getText().length() == 0
                        || emailView.getText().length() == 0){
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
                if(hostView.getText().length() == 0 || portView.getText().length() == 0 ||
                        usernameView.getText().length() == 0 || passwordView.getText().length() == 0){
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        hostView.addTextChangedListener(watcher);
        portView.addTextChangedListener(watcher);
        usernameView.addTextChangedListener(watcher);
        passwordView.addTextChangedListener(watcher);
        fNameView.addTextChangedListener(watcher);
        lNameView.addTextChangedListener(watcher);
        emailView.addTextChangedListener(watcher);

        return v;
    }

    private void attemptLogin() {
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        String server = hostView.getText().toString() +":"+ portView.getText().toString();

        loginTask = new LoginTask(username, password, server, getContext(), this);
        loginTask.execute();
    }

    private void attemptRegister(){
        String server = hostView.getText().toString() +":"+ portView.getText().toString();
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        String firstName = fNameView.getText().toString();
        String lastName = lNameView.getText().toString();
        String email = emailView.getText().toString();
        String gender;
        if(genderGroup.getCheckedRadioButtonId() == R.id.radioButtonMale){
            gender = "m";
        }
        else if(genderGroup.getCheckedRadioButtonId() == R.id.radioButtonFemale){
            gender = "f";
        }
        else{
            gender = " ";
        }

        registerTask = new RegisterTask(username, password, firstName, lastName, email, gender, server, getContext(), this);
        registerTask.execute();
    }

    public void getFamilyPersons(String authToken){
        String server = hostView.getText().toString() +":"+ portView.getText().toString();

        getFamilyTask = new GetFamilyTask(authToken, server, getContext());
        getFamilyTask.execute();
    }

    public void getFamilyEvents(String authToken){
        String server = hostView.getText().toString() +":"+ portView.getText().toString();

        getFamilyEventsTask = new GetFamilyEventsTask(authToken, server, getContext());
        getFamilyEventsTask.execute();
    }

    public void login(){
        MainActivity activity = (MainActivity) getActivity();
        activity.openMap();
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
