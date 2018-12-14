package main.java.com.liftsimulation.project;


import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    public static  int NUM_OF_FLOORS;
    public static final double MEAN_WORK = 3600.0; //Mean time spent working is 60.0 minutes (converted to seconds)
    public static final double LAMBDA_WORK = 1.0 / MEAN_WORK;

    //Times are in minutes
    public static final double START = 0.0;
    public static final double END = 86400; //Run simulation for 24 hours (converted to seconds)

    //Scheduling algorithms
    public static final int FCFS = 0;

    //Idling policies
    public static final int STAY = 0;

    //Random number generators
    private static Random r1 = new Random(12345); //Used for interarrival times
    private static Random r2 = new Random(54321); //Used for time spent working
    private static Random r3 = new Random(98765); //Used to pick a random floor
    private static Random r4 = new Random(35917); //Used for coin flips

    //User-configurable settings
    private static int numOfElevators = 1;
    private static int idlingPolicy = STAY;
    private static double lambda = 0.5 / 60.0; //0.5 people arrive per minute (converted into seconds)


    /*
    The key is the floor number (between 1 and NUM_OF_FLOORS),
    and the value is the Floor object associated with that floor number.
    */
    private static HashMap<Integer, Floor> floors = new HashMap<>();

    private static EventList eventList = new EventList();
    private static RequestList requestList = new RequestList();

    private static Elevator elevator1 = null;
    private static Elevator elevator2 = null;


    private static double clock;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Total Number of floors in the building: ");
        NUM_OF_FLOORS = sc.nextInt();

        System.out.println("Enter Number of Simulation/Lifts: ");
        numOfElevators = sc.nextInt();
        if(numOfElevators<1 || numOfElevators>2) {
            throw new InvalidArgumentException(new String[]{"Number of Elevators should be 1 or 2 "});
        }


        //Initialize elevators
        if (numOfElevators == 1) {
            elevator1 = new Elevator();
        } else if (numOfElevators == 2) {
            elevator1 = new Elevator();
            elevator2 = new Elevator();
        }

        //Initialize floors
        for (int i = 1; i <= NUM_OF_FLOORS; i++) {
            floors.put(i, new Floor(i));
        }

        clock = START;

        //Schedule first person arriving on the ground floor
        Person p = new Person(1, clock + getInterarrivalTime(), getWorkDuration(), getRandomFloor());
        eventList.addNewEvent(new Event(p.getArrivalTime(), p.getOriginFloor(), p));

        //Simulation loop
        Event event = eventList.getNextEvent();
        while (event.getTime() < END) {
            //Update the clock
            clock = event.getTime();

            if (event.isPersonEvent()) {
                //A person is approaching the elevator area on some floor
                Person person = event.getPerson();
                Floor floor = floors.get(event.getFloor());

                //If nobody else is waiting at the elevator area, request an elevator
                if (!floor.hasPeopleWaiting()) {
                    requestList.addPendingRequest(new Request(clock, floor.getId()));
                }
                floor.joinPeopleWaiting(person);

                if (floor.getId() == 1) {
                    //Schedule another person arriving on the ground floor
                    p = new Person(1, clock + getInterarrivalTime(), getWorkDuration(), getRandomFloor());
                    eventList.addNewEvent(new Event(p.getArrivalTime(), p.getOriginFloor(), p));
                }
                /*System.out.println("RequestList in personEvent: "+ requestList);
                System.out.println("EventList in personEvent: "+ eventList);*/
            } else {
                //An elevator is arriving on some floor
                Elevator elevator = event.getElevator();
                Floor floor = floors.get(event.getFloor());

                //Update elevator's floor to this floor
                elevator.setCurrentFloor(floor.getId());

                if (event.isPickUpEvent()) {
                    //Let people on
                    elevator.load(floor.removePeopleWaiting());
                    //Schedule the event for the elevator's next dropoff
                    eventList.addNewEvent(new Event(clock + elevator.getTravelTime(elevator.getNextFloor()), elevator.getNextFloor(), elevator, Event.DROPOFF_EVENT));
                    //Set elevator's status to busy
                    elevator.setBusy();
                } else if (event.isDropOffEvent()) {
                    //Let passengers off
                    ArrayList<Person> exitingPassengers = elevator.unload();
                    //Record user-perceived response time
                    //Schedule this person's leaving event
                    exitingPassengers.stream().filter(person -> floor.getId() > 1).forEach(person -> {
                        person.setOriginFloor(floor.getId());
                        person.setArrivalTime(clock + person.getWorkDuration());
                        person.setDestination(1);
                        eventList.addNewEvent(new Event(person.getArrivalTime(), person.getOriginFloor(), person));
                    });
                    //Record that this elevator successfully brought passengers to this floor
                    elevator.incrementRequestsServed();
                    if (!elevator.isEmpty()) {
                        //Schedule the event for the elevator's next dropoff
                        eventList.addNewEvent(new Event(clock + elevator.getTravelTime(elevator.getNextFloor()), elevator.getNextFloor(), elevator, Event.DROPOFF_EVENT));
                    } else {
                        //Check for pending requests
                        if (requestList.hasPendingRequest()) {
                            int nextRequestFloor = requestList.getFirstPendingRequest();
                            //Schedule the elevator arriving on the requested floor
                            eventList.addNewEvent(new Event(clock + elevator.getTravelTime(nextRequestFloor), nextRequestFloor, elevator, Event.PICKUP_EVENT));
                            //Remove the request from the request list to avoid double booking
                            requestList.removePendingRequest(nextRequestFloor);
                        } else {
                            if (idlingPolicy == STAY) {
                                elevator.setIdle();
                            } else {
                                //Schedule event for elevator arriving at bottom floor
                                eventList.addNewEvent(new Event(clock + elevator.getTravelTime(1), 1, elevator, Event.IDLE_EVENT));
                            }
                        }
                    }
                } else {
					/*
					Elevator has arrived at the bottom floor to idle.
					If there are no pending requests (some may have popped up while
					the elevator was traveling to its idling location), set the elevator's status to idle.
					*/
                    if (requestList.hasPendingRequest()) {
                        int nextRequestFloor = requestList.getFirstPendingRequest();
                        //Schedule the elevator arriving on the requested floor
                        eventList.addNewEvent(new Event(clock + elevator.getTravelTime(nextRequestFloor), nextRequestFloor, elevator, Event.PICKUP_EVENT));
                        //Remove the request from the request list to avoid double booking
                        requestList.removePendingRequest(nextRequestFloor);
                    } else {
                        elevator.setIdle();
                    }
                }
            }

			/*
			Check if there are any pending requests. If there is a pending request, check if there is
			an idle elevator that can service the request. If an idle elevator is found, schedule the pick up event.
			*/
            if (requestList.hasPendingRequest()) {
                Elevator availableElevator = getIdleElevator();
                if (availableElevator != null) {
                    int nextRequestFloor = requestList.getFirstPendingRequest();
                    //Schedule the elevator arriving on the requested floor
                    eventList.addNewEvent(new Event(clock + availableElevator.getTravelTime(nextRequestFloor), nextRequestFloor, availableElevator, Event.PICKUP_EVENT));
                    availableElevator.setBusy();
                    //Remove the request from the request list to avoid double booking
                    requestList.removePendingRequest(nextRequestFloor);
                }
            }

            //Get the next event
            event = eventList.getNextEvent();
            System.out.println("Next Event : " + event);
        }

    }

    /**
     * Generates a random interarrival time following an Exponential distribution.
     *
     * @return an interarrival time
     */
    public static double getInterarrivalTime() {
        double u = r1.nextDouble();
        double x = (-1.0 / lambda) * Math.log(1 - u);
        return x;
    }

    /**
     * Generates a random work duration following an Exponential distribution.
     *
     * @return a work duration
     */
    public static double getWorkDuration() {
        double u = r2.nextDouble();
        double x = (-1.0 / LAMBDA_WORK) * Math.log(1 - u);
        return x;
    }

    /**
     * Generates a random floor number that is not the ground floor.
     *
     * @return a random integer between 2 and NUM_OF_FLOORS
     */
    public static int getRandomFloor() {
        return r3.nextInt(NUM_OF_FLOORS - 1) + 2;
    }

    /**
     * This method checks for an idle elevator. If all elevators are idle,
     * one is chosen at random. If all elevators are busy, the method returns null.
     *
     * @return an idle elevator, or null if all elevators are busy
     */
    public static Elevator getIdleElevator() {
        if (numOfElevators == 1) {
            if (elevator1.isIdle()) {
                return elevator1;
            } else {
                return null;
            }
        } else if (numOfElevators == 2) {
            if (elevator1.isIdle() && elevator2.isIdle()) {
                //Flip a coin
                if (r4.nextInt(2) == 0) {
                    return elevator1;
                } else {
                    return elevator2;
                }
            } else if (!elevator1.isIdle() && !elevator2.isIdle()) {
                return null;
            } else if (elevator1.isIdle()) {
                return elevator1;
            } else {
                return elevator2;
            }
        }
        return null;
    }



}