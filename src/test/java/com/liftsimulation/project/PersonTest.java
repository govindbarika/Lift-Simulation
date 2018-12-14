package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class PersonTest {

    Person person;

    @Before
    public void setUp() throws Exception {
        person = new Person(1, 10.00, 00.10, 5);
    }

    @Test
    public void testSetOriginFloor() {
        person.setOriginFloor(2);
        Assert.assertEquals(2, person.getOriginFloor());
    }


    @Test
    public void testGetOriginFloor() {
        Assert.assertEquals(1, person.getOriginFloor());
    }

    @Test
    public void tstSetArrivalTime() {
        Assert.assertThat(10.00, equalTo(person.getArrivalTime()));
    }


    @Test
    public void tstSetArrivalTime_withDifferentTime() {
        person.setArrivalTime(11.00);
        Assert.assertThat(11.00, equalTo(person.getArrivalTime()));
    }


    @Test
    public void testGetArrivalTime() {
        Assert.assertThat(10.00, equalTo(person.getArrivalTime()));
    }

    @Test
    public void testSetWorkDuration() {
        Assert.assertThat(00.10, equalTo(person.getWorkDuration()));
        person.setWorkDuration(00.20);
        Assert.assertThat(00.20, equalTo(person.getWorkDuration()));
    }

    @Test
    public void testGetWorkDuration() {
        Assert.assertThat(00.10, equalTo(person.getWorkDuration()));
    }

    @Test
    public void testSetDestination() {
        Assert.assertEquals(5, person.getDestination());
        person.setDestination(8);
        Assert.assertEquals(8, person.getDestination());
    }

    @Test
    public void testGetDestination() {
        Assert.assertEquals(5, person.getDestination());
    }
}