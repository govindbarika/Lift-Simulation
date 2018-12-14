package main.java.com.liftsimulation.project;

public class Person {
    private int originFloor;
    private double arrivalTime;
    private double workDuration;
    private int destination;

    public Person(int originFloor, double arrivalTime, double workDuration, int destination) {
        setOriginFloor(originFloor);
        setArrivalTime(arrivalTime);
        setWorkDuration(workDuration);
        setDestination(destination);
    }

    public void setOriginFloor(int originFloor) {
        this.originFloor = originFloor;
    }

    public int getOriginFloor() {
        return originFloor;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setWorkDuration(double workDuration) {
        this.workDuration = workDuration;
    }

    public double getWorkDuration() {
        return workDuration;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Person{" +
                "originFloor=" + originFloor +
                ", arrivalTime=" + arrivalTime +
                ", workDuration=" + workDuration +
                ", destination=" + destination +
                '}';
    }
}