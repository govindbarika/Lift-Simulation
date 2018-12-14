package main.java.com.liftsimulation.project;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    ArrayList<Event> eventList;

    public EventList() {
        eventList = new ArrayList<>();
    }

    public void addNewEvent(Event newEvent) {
        int i = 0;
        while (i < eventList.size()) {
            if (newEvent.getTime() < eventList.get(i).getTime()) {
                break;
            } else {
                i++;
            }
        }
        eventList.add(i, newEvent);
    }

    //Retrieve the next (i.e. most imminent) event from the event list.
    public Event getNextEvent() {
        return eventList.remove(0);
    }

    public List<Event> getAllEvents(){
        return this.eventList;
    }

    @Override
    public String toString() {
        return "EventList{" +
                "eventList=" + eventList +
                '}';
    }
}