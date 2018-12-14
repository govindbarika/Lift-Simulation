package main.java.com.liftsimulation.project;


import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int id;
    private ArrayList<Person> peopleWaiting;

    public Floor(int id) {
        this.id = id;
        peopleWaiting = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    /**
     * This method adds a person to the list of people waiting in front of the elevators.
     *
     * @param person the person approaching the elevators
     */
    public void joinPeopleWaiting(Person person) {
        peopleWaiting.add(person);
    }

    /**
     * This method clears the list of people waiting in front of the elevators.
     * It is called when an elevator arrives to pick people up.
     *
     * @return the list of people waiting in front of the elevators
     */
    public ArrayList<Person> removePeopleWaiting() {
        ArrayList<Person> tmp = new ArrayList<>();
        tmp.addAll(peopleWaiting);
        peopleWaiting.clear();
        return tmp;
    }

    /**
     * This method checks if there are people waiting in front of the elevators.
     *
     * @return true if there are people on this floor waiting at the elevators, false otherwise
     */
    public boolean hasPeopleWaiting() {
        return !peopleWaiting.isEmpty();
    }

    public List<Person> getListOfPeopleWaiting(){
        return this.peopleWaiting;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", peopleWaiting=" + peopleWaiting +
                '}';
    }
}