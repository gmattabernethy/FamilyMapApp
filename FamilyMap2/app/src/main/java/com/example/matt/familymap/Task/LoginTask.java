package com.example.matt.familymap.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.matt.familymap.LoginFragment;
import com.example.matt.familymap.tool.API;
import com.example.matt.familymap.tool.Data;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
    LoginFragment fragment;

    private final String userName;
    private final String password;
    private String server;
    private Context context;
    private Data data;

    public LoginTask(String userName, String password, String server, Context context, LoginFragment fragment) {
        this.fragment = fragment;
        this.userName = userName;
        this.password = password;
        this.server = server;
        this.context = context;
        data = Data.buildData();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String url = "http://"+server+"/user/login";

        JSONObject request = new JSONObject();
        try {
            request.put("userName", userName);
            request.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(context,response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            String authToken = response.get("token").toString();
                            data.setAuthToken(authToken);
                            fragment.getFamilyPersons(authToken);
                            fragment.getFamilyEvents(authToken);
                            fragment.login();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String( error.networkResponse.data, "utf-8" );
                            JSONObject jsonObject = new JSONObject( responseBody );
                            System.out.println(jsonObject.get("message"));
                            Toast.makeText(context,jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                        } catch ( JSONException e ) {
                            //Handle a malformed json response
                        } catch (UnsupportedEncodingException e){

                        }
                    }
                });

        API.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return true;
    }

}
