package com.example.matt.familymap.tool;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;


public class Data {
    static private Data data;
    private JsonArray array;
    private Gson gson = new Gson();

    private class Location{
        private String country;
        private String city;
        private double latitude;
        private double longitude;
    }

    private Data(Context context) {

        String path = "data/locations.json";
        try {
            InputStream is = context.getAssets().open(path);
            Reader reader = new InputStreamReader(is);

            Gson gson = new Gson();
            JsonObject obj = gson.fromJson(reader, JsonObject.class);
            array = obj.get("data").getAsJsonArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Data buildData(Context context) {
        if (data == null) data = new Data(context);
        return data;
    }

    private Location randomLocation(){
        Location loc = new Location();

        Random rand = new Random();
        int i = rand.nextInt(array.size());
        JsonObject packet = array.get(i).getAsJsonObject();

        loc = gson.fromJson(packet.toString(),Location.class);

        return loc;
    }

}

