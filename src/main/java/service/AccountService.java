package service;

import domain.exceptions.BussinessExceptions;
import service.command.CreateAccountCommand;
import service.command.DepositOnAccountCommand;
import service.command.TransferBetweenAccountsCommand;
import service.command.WithdrawFromAccountCommand;
import service.dto.AccountDTO;
import service.dto.DepositDTO;
import service.dto.TransferenceDTO;
import service.dto.WithdrawDTO;
import service.exceptions.ServiceExceptions;

import java.util.Optional;

public interface AccountService {
    Optional<AccountDTO> getAccount(String id);
    AccountDTO createAccount(CreateAccountCommand createAccountCommand) throws ServiceExceptions.UserNotFound, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount;
    TransferenceDTO transfer(TransferBetweenAccountsCommand transferBetweenAccountsCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidAccountToTransfer;
    DepositDTO depositOnAccount(DepositOnAccountCommand depositOnAccountCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation;
    WithdrawDTO withdrawFromAccount(WithdrawFromAccountCommand withdrawFromAccountCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw;
}
