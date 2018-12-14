package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Elevator;
import main.java.com.liftsimulation.project.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;

public class ElevatorTest {

    Elevator elevator;

    private ArrayList<Person> passengers;

    @Before
    public void setUp() throws Exception {
        passengers = new ArrayList<>();
        elevator = new Elevator();
    }

    @Test
    public void testSetCurrentFloor() {
        elevator.setCurrentFloor(5);
        Assert.assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    public void testGetCurrentFloor() {
        elevator.setCurrentFloor(3);
        Assert.assertEquals(3, elevator.getCurrentFloor());
    }

    @Test
    public void testSetIdle() {
        elevator.setIdle();
        Assert.assertEquals(0, elevator.getStatus());
    }

    @Test
    public void testSetBusy() {
        elevator.setBusy();
        Assert.assertEquals(1, elevator.getStatus());
    }

    @Test
    public void testIsIdle() {
        elevator.setIdle();
        Assert.assertTrue(elevator.isIdle());
    }

    @Test
    public void incrementRequestsServed() {
        elevator.incrementRequestsServed();
        Assert.assertEquals(1, elevator.getRequestsServed());
    }

    @Test
    public void getRequestsServed() {
        elevator.incrementRequestsServed();
        elevator.incrementRequestsServed();
        elevator.incrementRequestsServed();
        Assert.assertEquals(3, elevator.getRequestsServed());
    }

    @Test
    public void testLoad() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(1, 10.00, 00.10, 5));
        persons.add(new Person(3, 11.00, 00.10, 8));
        elevator.load(persons);
        Assert.assertEquals(2, elevator.getAllPassengers().size());
        Assert.assertEquals(5, elevator.getAllPassengers().get(0).getDestination());
        Assert.assertEquals(1, elevator.getAllPassengers().get(0).getOriginFloor());
    }

    @Test
    public void testUnload() {

        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(2, 10.00, 00.10, 1));
        persons.add(new Person(3, 11.00, 00.10, 8));
        elevator.load(persons);
        elevator.setCurrentFloor(1);
        ArrayList<Person> unloadPersons = elevator.unload();
        Assert.assertEquals(1, unloadPersons.size());
        Assert.assertEquals(2, unloadPersons.get(0).getOriginFloor());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(elevator.isEmpty());
    }

    @Test
    public void getNextFloor() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(2, 10.00, 00.10, 5));
        elevator.load(persons);
        Assert.assertEquals(5, elevator.getNextFloor());
    }

    @Test
    public void getTravelTime() {
        elevator.setCurrentFloor(4);
        double time = elevator.getTravelTime(6);
        Assert.assertThat(20.0, equalTo(time));
    }


}