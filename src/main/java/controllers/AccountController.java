package controllers;

import controllers.exceptions.NotFoundItemException;
import controllers.request.*;

import service.AccountService;
import service.command.CreateAccountCommand;
import service.command.DepositOnAccountCommand;
import service.command.TransferBetweenAccountsCommand;
import service.command.WithdrawFromAccountCommand;

import spark.Request;
import spark.Response;
import spark.Route;
import java.util.function.Function;

public class AccountController extends Controller {

    public static AccountService accountService;

    public static Route get = (Request request, Response response) -> {
        String accountId = request.params(":id");

        return accountService.getAccount(accountId).orElseThrow(() -> new NotFoundItemException());
    };

    public static Route createAccount = (Request request, Response response) -> {
        Function<AccountCreateRequest, CreateAccountCommand> toCommand = accountCreateRequest -> CreateAccountCommand.newBuilder()
                .user(accountCreateRequest.getUser())
                .debtLimit(accountCreateRequest.getDebtLimit()).build();

        response.status(201);
        return execute(request.body(), AccountCreateRequest.class, toCommand, accountService::createAccount);
    };


    public static Route transferBetweenAccounts = (Request request, Response response) -> {
        Function<AccountTransferRequest, TransferBetweenAccountsCommand> toCommand = accountTransferRequest -> TransferBetweenAccountsCommand.newBuilder()
                .from(accountTransferRequest.getFrom())
                .to(accountTransferRequest.getTo())
                .value(accountTransferRequest.getValue())
                .build();

        return execute(request.body(), AccountTransferRequest.class, toCommand, accountService::transfer);
    };

    public static Route deposit = (Request request, Response response) -> {
        Function<DepositRequest, DepositOnAccountCommand> toCommand = depositRequest -> DepositOnAccountCommand.newBuilder()
                .account(depositRequest.getTo())
                .value(depositRequest.getValue())
                .build();

        return execute(request.body(), DepositRequest.class, toCommand, accountService::depositOnAccount);
    };

    public static Route withdraw = (Request request, Response response) -> {
        Function<WithdrawRequest, WithdrawFromAccountCommand> toCommand = withdrawRequest -> WithdrawFromAccountCommand.newBuilder()
                .account(withdrawRequest.getFrom())
                .value(withdrawRequest.getValue())
                .build();

        return execute(request.body(), WithdrawRequest.class, toCommand, accountService::withdrawFromAccount);
    };
}
