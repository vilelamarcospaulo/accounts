package controllers;

import app.Application;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.*;
import org.junit.runners.MethodSorters;
import service.dto.UserDTO;
import spark.Spark;
import utils.JsonUtils;

import java.io.IOException;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerRestTest extends GenericRestTest {
    @Before
    public void createServer(){
        Application.main(null);
        Spark.awaitInitialization();
    }

    @After
    public void stopServer() {
        Spark.stop();
        Spark.awaitStop();
    }

    @Test
    public void findUsersWithoutuser() {
        TestResponse testResponse = super.request("GET", "/user");
        Assert.assertEquals(200, testResponse.status);
        Assert.assertEquals("[]", testResponse.body);
    }

    @Test
    public void createUsers() {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
        "}");

        Assert.assertEquals(201, testResponse.status);
    }


    @Test
    public void findUsersWithUser() throws IOException {
        this.createUsers();
        TestResponse testResponse = super.request("GET", "/user");

        Assert.assertEquals(200, testResponse.status);

        List<UserDTO> users = JsonUtils.readFromJson(testResponse.body, new TypeReference<List<UserDTO>>(){});
        UserDTO user = users.get(0);

        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getCpf(), "123");
        Assert.assertEquals(user.getName(), "Jhon Doe");
    }

}