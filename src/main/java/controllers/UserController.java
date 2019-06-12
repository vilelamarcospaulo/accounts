package controllers;

import controllers.request.UserPostRequest;
import service.UserService;
import service.command.CreateUserCommand;
import service.dto.UserDTO;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.JsonUtils;

public class UserController {
    public static UserService userService;

    public static Route get = (Request request, Response response) -> {
        return userService.getUsers();
    };

    public static Route post = (Request request, Response response) -> {
        UserPostRequest userRequest = JsonUtils.readFromJson(request.body(), UserPostRequest.class);
        userRequest.validate();

        CreateUserCommand createUserCommand = CreateUserCommand.newBuilder().cpf(userRequest.getCpf()).name(userRequest.getName()).build();
        UserDTO user = userService.createUser(createUserCommand);

        return user;
    };
}
