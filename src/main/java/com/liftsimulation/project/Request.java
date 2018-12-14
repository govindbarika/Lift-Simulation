package main.java.com.liftsimulation.project;

public class Request {

    private double time;
    private int callingFloor;

    public Request() {

    }

    public Request(double time, int callingFloor) {
        setTime(time);
        setCallingFloor(callingFloor);
    }


    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setCallingFloor(int callingFloor) {
        this.callingFloor = callingFloor;
    }

    public int getCallingFloor() {
        return callingFloor;
    }

    @Override
    public String toString() {
        return "Request{" +
                "time=" + time +
                ", callingFloor=" + callingFloor +
                '}';
    }
}