package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Elevator;
import main.java.com.liftsimulation.project.Event;
import main.java.com.liftsimulation.project.EventList;
import main.java.com.liftsimulation.project.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;

public class EventListTest {
    private ArrayList<Event> eventList;
    private EventList eventListObj;
    private Person person;
    private Elevator elevator;

    @Before
    public void setUp() throws Exception {
        eventList = new ArrayList<>();
        eventListObj = new EventList();
        person = new Person(1, 10.00, 00.10, 5);
        elevator = new Elevator();
    }

    @Test
    public void testAddNewEvent() {
        eventListObj.addNewEvent(new Event(10.00, 1, person));
        eventListObj.addNewEvent(new Event(10.00, 1, elevator, Event.PICKUP_EVENT));
        Assert.assertEquals(2, eventListObj.getAllEvents().size());
        Assert.assertEquals(1, eventListObj.getAllEvents().get(0).getFloor());
    }

    @Test
    public void testGetNextEvent() {
        eventListObj.addNewEvent(new Event(10.00, 1, person));
        eventListObj.addNewEvent(new Event(10.00, 1, elevator, Event.PICKUP_EVENT));
        Event nextEvent = eventListObj.getNextEvent();
        Person person = nextEvent.getPerson();
        Assert.assertEquals(1, nextEvent.getFloor());
        Assert.assertEquals(5, person.getDestination());
        Assert.assertThat(00.10, equalTo(person.getWorkDuration()));
    }
}