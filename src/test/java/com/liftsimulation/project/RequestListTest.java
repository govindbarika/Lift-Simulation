package test.java.com.liftsimulation.project;

import main.java.com.liftsimulation.project.Request;
import main.java.com.liftsimulation.project.RequestList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RequestListTest {

    private RequestList requestListObj;
    private ArrayList<Request> requestList;

    @Before
    public void setUp() throws Exception {
        requestListObj = new RequestList();
        requestList = new ArrayList<>();
    }

    @Test
    public void testAddPendingRequest() {
        requestListObj.addPendingRequest(new Request(10.00, 8));
        Assert.assertNotNull(requestListObj.getRequestList());
        Assert.assertEquals(1, requestListObj.getRequestList().size());
    }

    @Test
    public void testAddPendingRequest_withoutAnyRequests_shouldReturn_EmptyList() {
        Assert.assertNotNull(requestListObj.getRequestList());
        Assert.assertEquals(0, requestListObj.getRequestList().size());
    }

    @Test
    public void testHasPendingRequestwithoutRequest() {
        RequestList requesEmptytList = new RequestList();
        Assert.assertFalse(requesEmptytList.hasPendingRequest());
    }

    @Test
    public void testHasPendingRequest() {
        RequestList requestValueList = new RequestList();
        requestValueList.addPendingRequest(new Request(10.00, 5));
        Assert.assertTrue(requestValueList.hasPendingRequest());
    }

    @Test
    public void testGetFirstPendingRequest() {
        requestListObj.addPendingRequest(new Request(10.00, 8));
        requestListObj.addPendingRequest(new Request(11.00, 4));
        int nextPendingFloor = requestListObj.getFirstPendingRequest();
        Assert.assertNotNull(nextPendingFloor);
        Assert.assertEquals(8, nextPendingFloor);
    }


    @Test
    public void testGetFirstPendingRequest_without_anyrequest_shouldreturndefaultvalueofint() {
        requestListObj.addPendingRequest(new Request());
        int nextPendingFloor = requestListObj.getFirstPendingRequest();
        Assert.assertEquals(0, nextPendingFloor);
    }

    @Test
    public void testRemovePendingRequest() {
        requestListObj.addPendingRequest(new Request(10.00, 8));
        requestListObj.addPendingRequest(new Request(11.00, 4));
        requestListObj.removePendingRequest(4);
        Assert.assertEquals(1, requestListObj.getRequestList().size());
    }

    @Test
    public void testRemovePendingRequest_EmptyRequestList() {
        requestListObj.removePendingRequest(5);
        Assert.assertEquals(0, requestListObj.getRequestList().size());
    }

    @Test
    public void testGetRequestList(){
        Assert.assertEquals(0, requestListObj.getRequestList().size());
    }

    @Test
    public void testGetRequestList_withvalues_shouldReturn_Numberofrequests(){
        requestListObj.addPendingRequest(new Request(10.00, 8));
        requestListObj.addPendingRequest(new Request(11.00, 4));
        Assert.assertEquals(2, requestListObj.getRequestList().size());
    }
}