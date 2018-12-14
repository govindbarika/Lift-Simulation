package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Elevator;
import main.java.com.liftsimulation.project.Event;
import main.java.com.liftsimulation.project.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class EventTest {
    private Person person;
    private Elevator elevator;
    Event personEvent;
    Event elevatorEvent;
    Event elevatorDropEvent;
    Event elevatorIdleEvent;

    @Before
    public void setUp() throws Exception {
        person = new Person(1, 10.00, 00.10, 5);
        elevator = new Elevator();
        personEvent = new Event(10.00, 1, person);
        elevatorEvent = new Event(10.10, 5, elevator, Event.PICKUP_EVENT);
        elevatorDropEvent = new Event(10.10, 5, elevator, Event.DROPOFF_EVENT);
        elevatorIdleEvent = new Event(10.10, 5, elevator, Event.IDLE_EVENT);
    }

    @Test
    public void testGetTime() {
        Assert.assertThat(10.00, equalTo(personEvent.getTime()));
        Assert.assertThat(10.10, equalTo(elevatorEvent.getTime()));

    }

    @Test
    public void testGetFloor() {
        Assert.assertEquals(1, personEvent.getFloor());
        Assert.assertEquals(5, elevatorEvent.getFloor());
    }

    @Test
    public void testGetPerson() {
        Assert.assertNotNull(personEvent.getPerson());
        Assert.assertEquals(1, person.getOriginFloor());
        Assert.assertEquals(5, person.getDestination());
    }

    @Test
    public void testGetElevator() {
        Assert.assertNotNull(elevatorEvent.getElevator());
    }

    @Test
    public void testIsPersonEvent() {
        Assert.assertTrue(personEvent.isPersonEvent());
    }

    @Test
    public void testIsElevatorEvent() {
        Assert.assertTrue(elevatorEvent.isElevatorEvent());
    }

    @Test
    public void testIsPickUpEvent() {
        Assert.assertTrue(elevatorEvent.isPickUpEvent());
    }

    @Test
    public void testIsDropOffEvent() {
        Assert.assertTrue(elevatorDropEvent.isDropOffEvent());
    }

    @Test
    public void testIsIdleEvent() {
        Assert.assertTrue(elevatorIdleEvent.isIdleEvent());
    }

    @Test
    public void testToString(){
        Assert.assertNotNull(elevatorEvent.toString());
    }
}