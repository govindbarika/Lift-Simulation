package test.java.com.liftsimulation.project;

import static org.hamcrest.core.IsEqual.equalTo;

import main.java.com.liftsimulation.project.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {

    Request request;

    @Before
    public void setUp() throws Exception {
        request = new Request(08.45, 2);
    }

    @Test
    public void testGetTime() {
        Assert.assertThat(08.45, equalTo(request.getTime()));
    }

    @Test
    public void testGetCallingFloor() {
        Assert.assertThat(2, equalTo(2));
    }

    @Test
    public void testGetTime_withoutInput_shouldReturn_defaultvalueofdouble() {
        request = new Request();
        Assert.assertThat(0.0, equalTo(request.getTime()));
    }

    @Test
    public void testGetCallingFloor_wihtoutInput_shouldReturn_defaultvalueofint() {
        Assert.assertThat(2, equalTo(2));
    }
}