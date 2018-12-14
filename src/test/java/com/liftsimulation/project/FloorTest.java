package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Floor;
import main.java.com.liftsimulation.project.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FloorTest {
    private ArrayList<Person> peopleWaiting;
    Floor floor;

    @Before
    public void setUp() throws Exception {
        floor = new Floor(1);
        peopleWaiting = new ArrayList<>();
    }

    @Test
    public void testGetId() {
        Assert.assertEquals(1, floor.getId());
    }

    @Test
    public void testJoinPeopleWaiting() {
        floor.joinPeopleWaiting(new Person(1, 10.00, 00.10, 5));
        Assert.assertEquals(1, floor.getListOfPeopleWaiting().size());
        Assert.assertEquals(5, floor.getListOfPeopleWaiting().get(0).getDestination());
    }

    @Test
    public void testRemovePeopleWaiting() {
        floor.joinPeopleWaiting(new Person(1, 10.00, 00.10, 5));
        floor.joinPeopleWaiting(new Person(2, 10.10, 00.10, 8));
        List<Person> removedPersonsList = floor.removePeopleWaiting();
        Assert.assertEquals(2, removedPersonsList.size());
        Assert.assertEquals(8, removedPersonsList.get(1).getDestination());
        Assert.assertEquals(0, floor.getListOfPeopleWaiting().size());

    }

    @Test
    public void testHasPeopleWaiting() {
        Assert.assertFalse(floor.hasPeopleWaiting());
    }

    @Test
    public void testHasPeopleWaiting_withTwopeoplewaiting() {
        floor.joinPeopleWaiting(new Person(1, 10.00, 00.10, 5));
        floor.joinPeopleWaiting(new Person(2, 10.10, 00.10, 8));
        Assert.assertTrue(floor.hasPeopleWaiting());
    }
}