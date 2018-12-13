package com.example.matt.familymap;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {
    private TextView fName;
    private TextView lName;
    private TextView gender;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    private Person person;
    private Data data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        data = Data.buildData();

        String personID = getIntent().getStringExtra("personID");

        person = data.getPersonByID(personID);
        fName = findViewById(R.id.person_fname);
        lName = findViewById(R.id.person_lname);
        gender = findViewById(R.id.person_gender);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        fillText();
    }

    private void fillText(){
        fName.setText(person.getFirstName());
        lName.setText(person.getLastName());
        if(person.getGender() == 'm') gender.setText("Male");
        else gender.setText("Female");
    }

    public class EventComparator implements Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            int yearDifference = e1.getYear() - e2.getYear();
            if(yearDifference != 0) return yearDifference;
            return e1.getEventType().toLowerCase().compareTo(e2.getEventType().toLowerCase());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortEvents(List<Event> events){
       events.sort(new EventComparator());

       for(int i = 0; i < events.size(); i++){
           Event e = events.get(i);
           if(e.getEventType().toLowerCase().equals("birth")) {
               events.set(0,e);
           }
           else if(e.getEventType().toLowerCase().equals("death")){
               events.set(events.size()-1,e);
           }
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Life Events");
        listDataHeader.add("Family");

        List<String> lifeEvents = fetchEventDescriptions();
        List<String> family = fetchFamilyDescriptions();

        listDataChild.put(listDataHeader.get(0), lifeEvents); // Header, Child data
        listDataChild.put(listDataHeader.get(1), family);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> fetchEventDescriptions(){
        List<String> lifeEvents = new ArrayList<String>();
        List<Event> events = data.getEventByPerson(person.getPersonID());

        sortEvents(events);

        for(Event e: events){
            String description = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
            lifeEvents.add(description);
        }

        return lifeEvents;
    }

    private List<String> fetchFamilyDescriptions(){
        Map familyMembers = data.getFamily(person.getPersonID());
        List<String> family = new ArrayList<String>();

        List<Person> persons = (ArrayList)familyMembers.get("father");
        if(persons.size() > 0) {
            Person father = persons.get(0);
            family.add(father.getFirstName() + " " + father.getLastName() + "\n" + "Father");
        }

        persons = (ArrayList)familyMembers.get("mother");
        if(persons.size() > 0){
            Person mother = persons.get(0);
            family.add(mother.getFirstName() + " " + mother.getLastName() + "\n" + "Mother");
        }

        persons = (ArrayList)familyMembers.get("spouse");
        if(persons.size() > 0) {
            Person spouse = persons.get(0);
            family.add(spouse.getFirstName() + " " + spouse.getLastName() + "\n" + "Spouse");
        }

        List<Person> children = (ArrayList)familyMembers.get("children");

        for(Person p: children){
            family.add(p.getFirstName() + " " + p.getLastName() + "\n" + "Child");
        }

        return family;
    }
}
