package controllers;

import controllers.request.UserPostRequest;
import service.UserService;
import service.command.CreateUserCommand;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Function;

public class UserController extends Controller {
    public static UserService userService;

    public static Route get = (Request request, Response response) -> {
        return userService.getUsers();
    };

    public static Route post = (Request request, Response response) -> {
        Function<UserPostRequest, CreateUserCommand> toCommand = userRequest ->
                CreateUserCommand.newBuilder().cpf(userRequest.getCpf()).name(userRequest.getName()).build();

        response.status(201);
        return execute(request.body(), UserPostRequest.class, toCommand, userService::createUser);
    };
}
