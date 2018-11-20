package com.example.matt.familymap;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.matt.familymap.API;
import com.example.matt.familymap.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

public class RegisterTask extends AsyncTask<Void, Void, Boolean> {
    private LoginFragment fragment;

    private final String userName;
    private final String password;
    private String fName;
    private String lName;
    private String email;
    private String gender;
    private String server;
    private Context context;

    public RegisterTask(String userName, String password, String fName, String lName, String email, String gender, String server, Context context, LoginFragment fragment) {
        this.fragment = fragment;
        this.userName = userName;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.gender = gender;
        this.server = server;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String url = "http://"+server+"/user/register";

        JSONObject request = new JSONObject();
        try {
            request.put("userName", userName);
            request.put("password", password);
            request.put("email", email);
            request.put("firstName", fName);
            request.put("lastName", lName);
            request.put("gender", gender);
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
                            fragment.getFamilyData(authToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Response: " + response.toString());
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        API.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return true;
    }
}

