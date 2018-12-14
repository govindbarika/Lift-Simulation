package main.java.com.liftsimulation.project;

public class Event {
    public static final int PERSON_EVENT = 0;
    public static final int PICKUP_EVENT = 1;
    public static final int DROPOFF_EVENT = 2;
    public static final int IDLE_EVENT = 3;

    private double time;
    private int floor;
    private int type;

    //Only one of these will be instantiated
    private Person person;
    private Elevator elevator;

    public Event(double time, int floor, Person person) {
        this.time = time;
        this.floor = floor;
        this.person = person;
        type = PERSON_EVENT;
    }

    public Event(double time, int floor, Elevator elevator, int type) {
        this.time = time;
        this.floor = floor;
        this.elevator = elevator;
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public int getFloor() {
        return floor;
    }

    public Person getPerson() {
        return person;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public boolean isPersonEvent() {
        return type == PERSON_EVENT;
    }

    public boolean isElevatorEvent() {
        return type == PICKUP_EVENT || type == DROPOFF_EVENT || type == IDLE_EVENT;
    }

    public boolean isPickUpEvent() {
        return type == PICKUP_EVENT;
    }

    public boolean isDropOffEvent() {
        return type == DROPOFF_EVENT;
    }

    public boolean isIdleEvent() {
        return type == IDLE_EVENT;
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", floor=" + floor +
                ", type=" + type +
                ", person=" + person +
                ", elevator=" + elevator +
                '}';
    }
}