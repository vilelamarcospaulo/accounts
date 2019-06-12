package service.impl;

import datastore.AccountRepository;
import datastore.UserRepository;
import domain.Account;
import domain.User;
import domain.accountoperations.Deposit;
import domain.accountoperations.Transference;
import domain.accountoperations.Withdraw;
import domain.exceptions.BussinessExceptions;
import org.mapstruct.factory.Mappers;
import service.AccountService;
import service.command.CreateAccountCommand;
import service.command.DepositOnAccountCommand;
import service.command.TransferBetweenAccountsCommand;
import service.command.WithdrawFromAccountCommand;
import service.dto.AccountDTO;
import service.dto.DepositDTO;
import service.dto.TransferenceDTO;
import service.dto.WithdrawDTO;
import service.exceptions.ServiceExceptions;
import service.mappers.AccountMapper;
import service.mappers.AccountOperationMapper;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    private AccountMapper accountMapper;
    private AccountOperationMapper accountOperationMapper;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;

        this.accountMapper = Mappers.getMapper(AccountMapper.class);
        this.accountOperationMapper = Mappers.getMapper(AccountOperationMapper.class);
    }

    public Optional<AccountDTO> getAccount(String id) {
        return this.accountRepository.find(id).map(this.accountMapper::domainToDto);
    }

    public AccountDTO createAccount(CreateAccountCommand createAccountCommand) throws ServiceExceptions.UserNotFound, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        User user = this.userRepository.find(createAccountCommand.getUser()).orElseThrow(() -> new ServiceExceptions.UserNotFound(createAccountCommand.getUser()));

        return this.accountMapper.domainToDto(
                this.accountRepository.save(
                        user.createAccount(createAccountCommand.getDebtLimit())));
    }

    public TransferenceDTO transfer(TransferBetweenAccountsCommand transferBetweenAccountsCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidAccountToTransfer {
        Account accountFrom = this.findByAccountNumberOrThrowNotFound(transferBetweenAccountsCommand.getFrom());
        Account accountTo = this.findByAccountNumberOrThrowNotFound(transferBetweenAccountsCommand.getTo());

        Transference transference = accountFrom.tranferTo(accountTo, transferBetweenAccountsCommand.getValue());
        return this.accountOperationMapper.tranferenceToDTO(transference);
    }

    public DepositDTO depositOnAccount(DepositOnAccountCommand depositOnAccountCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation {
        Account accountTo = this.findByAccountNumberOrThrowNotFound(depositOnAccountCommand.getAccount());
        Deposit deposit = accountTo.deposit(depositOnAccountCommand.getValue());

        return this.accountOperationMapper.depositToDTO(deposit);
    }

    public WithdrawDTO withdrawFromAccount(WithdrawFromAccountCommand withdrawFromAccountCommand) throws ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw {
        Account accountTo = this.findByAccountNumberOrThrowNotFound(withdrawFromAccountCommand.getAccount());
        Withdraw withdraw = accountTo.withdraw(withdrawFromAccountCommand.getValue());

        return this.accountOperationMapper.withdrawToDTO(withdraw);
    }

    private Account findByAccountNumberOrThrowNotFound(String accountNumber) throws ServiceExceptions.AccountNotFound {
        return this.accountRepository
                .find(accountNumber)
                .orElseThrow(() -> {
                    ServiceExceptions.AccountNotFound ex = new ServiceExceptions.AccountNotFound();
                    ex.addParametter(accountNumber);
                    return ex;
                });
    }
}
