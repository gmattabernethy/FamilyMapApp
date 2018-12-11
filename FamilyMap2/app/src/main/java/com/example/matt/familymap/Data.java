package com.example.matt.familymap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class Data {
    static private Data data;
    private List<Person> persons;
    private List<Event> events;
    Gson gson = new Gson();

    private Data(){
        persons = new ArrayList<>();
        events = new ArrayList<>();
    }

    public static Data buildData(){
        if(data == null) data = new Data();
        return data;
    }

    public void personsFromJson(String json){
        JsonArray jArray = gson.fromJson(json, JsonArray.class);

        for (int i = 0; i < jArray.size(); i++){
            JsonObject jObject = (JsonObject) jArray.get(i);
            Person person = gson.fromJson(jObject, Person.class);
            persons.add(person);
        }
    }

    public void eventsFromJson(String json){
        JsonArray jArray = gson.fromJson(json, JsonArray.class);

        for (int i = 0; i < jArray.size(); i++){
            JsonObject jObject = (JsonObject) jArray.get(i);
            Event event = gson.fromJson(jObject, Event.class);
            events.add(event);
        }
    }

    public Person getPerson(String personID){
        for (Person p: persons) {
            if(p.getPersonID().equals(personID)) return p;
        }
        return null;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }
}
