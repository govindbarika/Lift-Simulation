package main.java.com.liftsimulation.project;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    public static final int IDLE = 0;
    public static final int BUSY = 1;
    public static final double TIME_BETWEEN_FLOORS = 10.0; //Time to travel to adjacent floor is 10.0 seconds

    private int currentFloor;
    private ArrayList<Person> passengers;
    private int status;
    private int requestsServed;

    public Elevator() {
        setCurrentFloor(1); //All elevators are initially on ground floor
        passengers = new ArrayList<>();
        setIdle(); //All elevators are initially idle
        requestsServed = 0;
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setIdle() {
        status = IDLE;
    }

    public void setBusy() {
        status = BUSY;
    }

    public boolean isIdle() {
        return status == IDLE;
    }


    public int getStatus() {
        return status;
    }

    public void incrementRequestsServed() {
        requestsServed++;
    }

    public int getRequestsServed() {
        return requestsServed;
    }

    /**
     * Loads passengers into the elevator.
     * The pasengers are stored in order of increasing destination floor.
     *
     * @param newPassengers the list of people getting on the elevator
     */
    public void load(ArrayList<Person> newPassengers) {
        for (Person person : newPassengers) {
            int i = 0;
            while (i < passengers.size()) {
                if (person.getDestination() < passengers.get(i).getDestination()) {
                    break;
                } else {
                    i++;
                }
            }
            passengers.add(i, person);
        }
    }

    /**
     * Unloads passengers from the elevator.
     *
     * @return the list of people getting off the elevator
     */
    public ArrayList<Person> unload() {
        ArrayList<Person> exitingPassengers = passengers.stream().filter(passenger -> passenger.getDestination() == currentFloor).collect(Collectors.toCollection(ArrayList::new));
		/*
		Since the passengers are already ordered by increasing destination floor,
		keep popping off the passenger at the head of the list until reaching a
		passenger whose destination floor is not the elevator's current floor.
		*/
        passengers.removeAll(exitingPassengers);
        return exitingPassengers;
    }

    /**
     * This method checks if the elevator currently contains any passengers.
     *
     * @return true if the elevator is empty, false otherwise
     */
    public boolean isEmpty() {
        return passengers.size() == 0;
    }

    /**
     * This method determines which floor the elevator should go to next.
     *
     * @return the floor number that the elevator should go to next
     */
    public int getNextFloor() {
        return passengers.get(0).getDestination();
    }

    /**
     * This method determines the time it will take for the elevator
     * to get to a given floor from its current floor.
     *
     * @param destination the floor the elevator will go to
     * @return the amount of time it will take the elevator to get to the new floor
     */
    public double getTravelTime(int destination) {
        int difference = Math.abs(currentFloor - destination);
        return difference * TIME_BETWEEN_FLOORS;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                ", passengers=" + passengers +
                ", status=" + status +
                ", requestsServed=" + requestsServed +
                '}';
    }

    public List<Person> getAllPassengers(){
        return passengers;
    }
}