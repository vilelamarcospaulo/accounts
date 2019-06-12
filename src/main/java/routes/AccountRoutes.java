package routes;

import controllers.AccountController;
import datastore.impl.AccountRepositoryImpl;
import datastore.impl.UserRepositoryImpl;
import service.impl.AccountServiceImpl;
import spark.Spark;
import utils.JsonUtils;

public class AccountRoutes {
    public static void initRoutes() {
        // dependencies
        AccountController.accountService = new AccountServiceImpl(UserRepositoryImpl.getInstance(), AccountRepositoryImpl.getInstance());

        // routes
        Spark.post("/account", AccountController.createAccount, JsonUtils::writeToJson);
        Spark.post("/account/transfer", AccountController.transferBetweenAccounts, JsonUtils::writeToJson);
        Spark.post("/account/deposit", AccountController.deposit, JsonUtils::writeToJson);
        Spark.post("/account/withdraw", AccountController.withdraw, JsonUtils::writeToJson);
        Spark.get("/account/:id", AccountController.get, JsonUtils::writeToJson);
    }
}
