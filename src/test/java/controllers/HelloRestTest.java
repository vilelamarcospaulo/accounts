package controllers;

import app.Application;
import org.junit.*;
import org.junit.runners.MethodSorters;
import spark.Spark;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloRestTest extends GenericRestTest {
    @BeforeClass
    public static void startServer() {
        Application.main(null);
        Spark.awaitInitialization();
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
        Spark.awaitStop();
    }

    @Test
    public void helloTest(){
        TestResponse testResponse = super.request("GET", "/hello");
        Assert.assertEquals(200, testResponse.status);
        Assert.assertEquals("world", testResponse.body);
    }

}