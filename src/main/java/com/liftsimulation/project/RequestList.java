package main.java.com.liftsimulation.project;

import java.util.ArrayList;
import java.util.List;

public class RequestList {
    private ArrayList<Request> requestList;



    public RequestList() {
        requestList = new ArrayList<>();
    }


    public void addPendingRequest(Request request) {
        int i = 0;
        while (i < requestList.size()) {
            if (request.getTime() < requestList.get(i).getTime()) {
                break;
            } else {
                i++;
            }
        }
        requestList.add(i, request);
    }

    /**
     * This method checks if there are any requests to summon an elevator
     *
     * @return true of there are any floors requesting an elevator, false otherwise
     */
    public boolean hasPendingRequest() {
        return requestList.size() > 0;
    }

    /**
     * This method determines the next request based on timestamp.
     *
     * @return the floor number of the next request
     */
    public int getFirstPendingRequest() {
        return requestList.get(0).getCallingFloor();
    }

    /**
     * This method removes a request to a given floor from the queue.
     * @param floor the floor of the request to remove
     */
    public void removePendingRequest(int floor) {
        Request request = null;
        for (Request aRequestList : requestList) {
            request = aRequestList;
            if (request.getCallingFloor() == floor) {
                break;
            }
        }
        requestList.remove(request);
    }

    public List<Request> getRequestList(){
        return this.requestList;
    }

    @Override
    public String toString() {
        return "RequestList{" +
                "requestList=" + requestList +
                '}';
    }
}