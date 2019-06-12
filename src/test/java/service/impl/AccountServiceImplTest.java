package service.impl;

import datastore.AccountRepository;

import datastore.UserRepository;
import domain.Account;
import domain.User;
import domain.exceptions.BussinessExceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceImplTest {
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    private AccountService accountService;

    @Before
    public void setup() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.accountRepository = Mockito.mock(AccountRepository.class);

        this.accountService = new AccountServiceImpl(this.userRepository,  this.accountRepository);
    }

    @Test
    public void createAccount() throws ServiceExceptions.UserNotFound, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        User mockedUser = User.createUser("123", "Jhon Doe");

        CreateAccountCommand createAccountCommand = CreateAccountCommand.newBuilder()
                .user("1")
                .debtLimit(BigDecimal.ZERO)
                .build();

        Mockito
        .when(this.userRepository.find("1"))
        .thenReturn(Optional.of(mockedUser));

        Mockito
        .when(this.accountRepository.save(Mockito.any()))
        .thenReturn(Account.createAccount(mockedUser, BigDecimal.ZERO));

        // Execute
        AccountDTO accountDTO = this.accountService.createAccount(createAccountCommand);

        // Assert
        Assert.assertNotNull(accountDTO);
    }

    @Test
    public void getAccount() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        //MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        //EXECUTE
        Optional<AccountDTO> accountDTO = this.accountService.getAccount(mockedAccount.getAccountNumber());

        //ASSERT
        Assert.assertTrue(accountDTO.isPresent());
    }

    @Test
    public void getAccountNotFound() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        //MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find("2"))
        .thenReturn(Optional.of(mockedAccount));

        //EXECUTE
        Optional<AccountDTO> accountDTO = this.accountService.getAccount("1");

        //ASSERT
        Assert.assertFalse(accountDTO.isPresent());
    }


    @Test
    public void transfer() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.TEN);
        mockedAccount.deposit(BigDecimal.TEN);

        Account mockedAccount2 =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        Mockito
        .when(this.accountRepository.find(mockedAccount2.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount2));

        // EXECUTE
        TransferBetweenAccountsCommand transferBetweenAccountsCommand = TransferBetweenAccountsCommand.newBuilder()
                .from(mockedAccount.getAccountNumber())
                .to(mockedAccount2.getAccountNumber())
                .value(BigDecimal.TEN)
                .build();

        TransferenceDTO transference = this.accountService.transfer(transferBetweenAccountsCommand);

        // ASSERT
        Mockito.verify(this.accountRepository, Mockito.times(1)).find(mockedAccount.getAccountNumber());
        Mockito.verify(this.accountRepository, Mockito.times(1)).find(mockedAccount2.getAccountNumber());

        Assert.assertEquals(mockedAccount.getBalance(), BigDecimal.ZERO);
        Assert.assertEquals(mockedAccount2.getBalance(), BigDecimal.TEN);

        Assert.assertNotNull(transference);
    }


    @Test(expected = BussinessExceptions.InsulficientFoundsForTransfer.class)
    public void transferWithoutFound() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.ZERO);
        Account mockedAccount2 =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        Mockito
        .when(this.accountRepository.find(mockedAccount2.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount2));

        // EXECUTE
        TransferBetweenAccountsCommand transferBetweenAccountsCommand = TransferBetweenAccountsCommand.newBuilder()
        .from(mockedAccount.getAccountNumber())
        .to(mockedAccount2.getAccountNumber())
        .value(BigDecimal.TEN)
        .build();

        TransferenceDTO transference = this.accountService.transfer(transferBetweenAccountsCommand);
    }

    @Test(expected = ServiceExceptions.AccountNotFound.class)
    public void transferFromAccountNotFound() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.ZERO);
        Account mockedAccount2 =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(""))
        .thenReturn(Optional.of(mockedAccount));

        Mockito
        .when(this.accountRepository.find(mockedAccount2.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount2));

        // EXECUTE
        TransferBetweenAccountsCommand transferBetweenAccountsCommand = TransferBetweenAccountsCommand.newBuilder()
                .from(mockedAccount.getAccountNumber())
                .to(mockedAccount2.getAccountNumber())
                .value(BigDecimal.TEN)
                .build();

        TransferenceDTO transference = this.accountService.transfer(transferBetweenAccountsCommand);
    }

    @Test(expected = ServiceExceptions.AccountNotFound.class)
    public void transferToAccountNotFound() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.ZERO);
        Account mockedAccount2 =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        Mockito
        .when(this.accountRepository.find(""))
        .thenReturn(Optional.of(mockedAccount2));

        // EXECUTE
        TransferBetweenAccountsCommand transferBetweenAccountsCommand = TransferBetweenAccountsCommand.newBuilder()
                .from(mockedAccount.getAccountNumber())
                .to(mockedAccount2.getAccountNumber())
                .value(BigDecimal.TEN)
                .build();

        TransferenceDTO transference = this.accountService.transfer(transferBetweenAccountsCommand);
    }

    @Test
    public void depositOnAccount() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.ZERO);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        // EXECUTE
        DepositOnAccountCommand depositOnAccountCommand = DepositOnAccountCommand.newBuilder()
                .account(mockedAccount.getAccountNumber())
                .value(BigDecimal.TEN)
                .build();

        DepositDTO depositDTO = this.accountService.depositOnAccount(depositOnAccountCommand);

        // ASSERT
        Assert.assertNotNull(depositDTO);
        Assert.assertEquals(mockedAccount.getBalance(), BigDecimal.TEN);
    }

    @Test(expected = ServiceExceptions.AccountNotFound.class)
    public void depositOnAccountNotFound() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.ZERO);

        Mockito
                .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
                .thenReturn(Optional.of(mockedAccount));

        // EXECUTE
        DepositOnAccountCommand depositOnAccountCommand = DepositOnAccountCommand.newBuilder()
        .account("123")
        .value(BigDecimal.TEN)
        .build();

        this.accountService.depositOnAccount(depositOnAccountCommand);
    }

    @Test
    public void withdrawFromAccount() throws BussinessExceptions.InvalidValueToOperation, ServiceExceptions.AccountNotFound, BussinessExceptions.InsulficientFoundsForWithdraw, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");
        Account mockedAccount =  Account.createAccount(mockedUser, BigDecimal.TEN);

        Mockito
        .when(this.accountRepository.find(mockedAccount.getAccountNumber()))
        .thenReturn(Optional.of(mockedAccount));

        // EXECUTE
        WithdrawFromAccountCommand withdrawFromAccountCommand = WithdrawFromAccountCommand.newBuilder()
                .account(mockedAccount.getAccountNumber())
                .value(BigDecimal.valueOf(5))
                .build();

        WithdrawDTO withdrawDTO = this.accountService.withdrawFromAccount(withdrawFromAccountCommand);

        // ASSERT
        Assert.assertNotNull(withdrawDTO );
        Assert.assertEquals(mockedAccount.getBalance(), BigDecimal.valueOf(-5));
    }

}