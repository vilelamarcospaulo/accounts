package routes;

import controllers.UserController;
import datastore.impl.UserRepositoryImpl;
import service.impl.UserServiceImpl;
import spark.Spark;
import utils.JsonUtils;

public class UserRoutes {
    public static void initRoutes() {
        // dependencies
        UserController.userService = new UserServiceImpl(UserRepositoryImpl.getInstance());

        // routes
        Spark.get("/user", UserController.get, JsonUtils::writeToJson);
        Spark.post("/user", UserController.post, JsonUtils::writeToJson);

    }
}
