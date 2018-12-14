package com.example.matt.familymap.tool;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;

public class Data {
    static private Data data;
    private List<Person> persons;
    private List<Event> events;
    private String authToken;
    private String server;
    private Gson gson = new Gson();

    private Data(){
        persons = new ArrayList<>();
        events = new ArrayList<>();
    }

    public static Data buildData(){
        if(data == null) data = new Data();
        return data;
    }

    public void personsFromJson(String json){
        persons = new ArrayList<>();
        JsonArray jArray = gson.fromJson(json, JsonArray.class);

        for (int i = 0; i < jArray.size(); i++){
            JsonObject jObject = (JsonObject) jArray.get(i);
            Person person = gson.fromJson(jObject, Person.class);
            persons.add(person);
        }
    }

    public void eventsFromJson(String json){
        events = new ArrayList<>();
        JsonArray jArray = gson.fromJson(json, JsonArray.class);

        for (int i = 0; i < jArray.size(); i++){
            JsonObject jObject = (JsonObject) jArray.get(i);
            Event event = gson.fromJson(jObject, Event.class);
            events.add(event);
        }
    }

    public Person getPersonByID(String personID){
        for (Person p: persons) {
            if(p.getPersonID().equals(personID)) return p;
        }
        return null;
    }

    public Event getEventByID(String eventID){
        for (Event e: events) {
            if(e.getEventID().equals(eventID)) return e;
        }
        return null;
    }

    public List<Event> getEventByPerson(String personID){
        List<Event> personEvents = new ArrayList<>();

        for (Event e: events) {
            if(e.getPersonID().equals(personID)) personEvents.add(e);
        }
        return personEvents;
    }

    public Map getFamily(String personID){
        Person person = getPersonByID(personID);
        List<Person> children = new ArrayList<>();
        List<Person> father = new ArrayList<>();
        List<Person> mother  = new ArrayList<>();
        List<Person> spouse  = new ArrayList<>();
        Map family = new HashMap();

        for (Person p: persons){
            if(p.getFather() != null){
                if(p.getFather().equals(personID)) children.add(p);
            }
            if(p.getMother() != null){
                if(p.getMother().equals(personID)){
                    children.add(p);
                }
            }
            if(person.getFather() != null) {
                if (p.getPersonID().equals(person.getFather())) father.add(p);
            }
            if(person.getMother() != null) {
                if(p.getPersonID().equals(person.getMother())) mother.add(p);
            }
            if(person.getSpouse() != null){
                if(p.getPersonID().equals(person.getSpouse())) spouse.add(p);
            }
        }

        family.put("children", children);
        family.put("father", father);
        family.put("mother", mother);
        family.put("spouse", spouse);

        return family;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }
}
