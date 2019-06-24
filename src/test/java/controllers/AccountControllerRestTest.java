package controllers;

import app.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.dto.AccountDTO;
import service.dto.UserDTO;
import spark.Spark;
import utils.JsonUtils;

import java.io.IOException;

public class AccountControllerRestTest extends GenericRestTest {
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
    public void findAccountNotExistent() {
        TestResponse testResponse = super.request("GET", "/account/1");

        Assert.assertEquals(404, testResponse.status);
    }


    @Test
    public void createAccountWithNotExistentUser() {
        TestResponse testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"123\",\n" +
                "\"debtLimit\": 10\n" +
        "}");
        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"NOF02 - User 123 not found\"}", testResponse.body);
    }


    @Test
    public void createAccount() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
        "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
        "}");

        Assert.assertEquals(201, testResponse.status);
    }

    @Test
    public void depositOnAccountNotExistent() throws IOException {
        TestResponse testResponse = super.request("POST", "/account/deposit", "{\n" +
                "\t\"to\":\"1\",\n" +
                "\t\"value\": 10\n" +
            "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"NOF01 - Account 1 not found\"}", testResponse.body);
    }

    @Test
    public void depositOnAccount() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/deposit", "{\n" +
                "\t\"to\":\"" + createdAccount.getAccountNumber() + "\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(200, testResponse.status);
    }

    @Test
    public void withdrawFromAccountNotExistent() throws IOException {
        TestResponse testResponse = super.request("POST", "/account/withdraw", "{\n" +
                "\t\"from\":\"1\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"NOF01 - Account 1 not found\"}", testResponse.body);
    }

    @Test
    public void withdrawFromAccount() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/withdraw", "{\n" +
                "\t\"from\":\"" + createdAccount.getAccountNumber() + "\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(200, testResponse.status);
    }


    @Test
    public void withdrawFromAccountNoFounds() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/withdraw", "{\n" +
                "\t\"from\":\"" + createdAccount.getAccountNumber() + "\",\n" +
                "\t\"value\": 50\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"LIM01 - Can`t withdraw this value, no limit\"}", testResponse.body);

    }

    @Test
    public void tranferFromAccountNotExistent() throws IOException {
        TestResponse testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"1\",\n" +
                "\t\"to\":\"2\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"NOF01 - Account 1 not found\"}", testResponse.body);
    }

    @Test
    public void tranferToAccountNotExistent() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"" +  createdAccount.getAccountNumber() +"\",\n" +
                "\t\"to\":\"2\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"NOF01 - Account 2 not found\"}", testResponse.body);
    }


    @Test
    public void tranferToAccount() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount1 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount2 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"" +  createdAccount1.getAccountNumber() +"\",\n" +
                "\t\"to\":\"" +  createdAccount2.getAccountNumber() +"\",\n" +
                "\t\"value\": 10\n" +
            "}");

        Assert.assertEquals(200, testResponse.status);
    }

    @Test
    public void tranferToSameAccount() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount1 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"" +  createdAccount1.getAccountNumber() +"\",\n" +
                "\t\"to\":\"" +  createdAccount1.getAccountNumber() +"\",\n" +
                "\t\"value\": 10\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"INV04 - Can`t transfer to same account of origin\"}", testResponse.body);
    }

    @Test
    public void tranferNegativeValue() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount1 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount2 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"" +  createdAccount1.getAccountNumber() +"\",\n" +
                "\t\"to\":\"" +  createdAccount2.getAccountNumber() +"\",\n" +
                "\t\"value\": -10\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"INV01 - Invalid value to operation withdraw, value can`t be null or less than zero\"}", testResponse.body);
    }

    @Test
    public void tranferWithoutFounds() throws IOException {
        TestResponse testResponse = super.request("POST", "/user", "{\n" +
                "\"cpf\": \"123\",\n" +
                "\"name\": \"Jhon Doe\"\n" +
                "}");

        UserDTO createdUser = JsonUtils.readFromJson(testResponse.body, UserDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount1 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account", "{\n" +
                "\"user\": \"" + createdUser.getId() + "\",\n" +
                "\"debtLimit\": 10\n" +
                "}");

        AccountDTO createdAccount2 = JsonUtils.readFromJson(testResponse.body, AccountDTO.class);

        testResponse = super.request("POST", "/account/transfer", "{\n" +
                "\t\"from\":\"" +  createdAccount1.getAccountNumber() +"\",\n" +
                "\t\"to\":\"" +  createdAccount2.getAccountNumber() +"\",\n" +
                "\t\"value\": 50\n" +
                "}");

        Assert.assertEquals(412, testResponse.status);
        Assert.assertEquals("{\"message\":\"LIM02 - Can`t transfer this value, no suficient founds\"}", testResponse.body);
    }
}