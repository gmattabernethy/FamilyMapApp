package Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.matt.familymap.API;
import com.example.matt.familymap.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GetFamilyTask extends AsyncTask<Void, Void, Boolean> {

    private String authToken;
    private String server;
    private Context context;
    private Data data;

    public GetFamilyTask(String authToken, String server, Context context) {
        this.authToken = authToken;
        this.server = server;
        this.context = context;
        data = Data.buildData();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String url = "http://"+server+"/person";

        JSONObject request = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jArray = (JSONArray) response.get("data");
                            data.personsFromJson(jArray.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        //headers.put("Content-Type", "application/json");
                        headers.put("Authorization", authToken);
                        return headers;
                    }
            };

        try {
            jsonObjectRequest.getHeaders();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        API.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return true;
    }

}
