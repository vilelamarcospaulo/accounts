package service.impl;

import datastore.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.UserService;
import service.command.CreateUserCommand;
import service.dto.UserDTO;

import domain.User;

import java.util.ArrayList;
import java.util.Optional;

public class UserServiceImplTest {
    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void setup() {
        this.userRepository = Mockito.mock(UserRepository.class);

        this.userService = new UserServiceImpl(this.userRepository);
    }


    @Test
    public void getUser() {
        //MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");

        Mockito
        .when(this.userRepository.find("1"))
        .thenReturn(Optional.of(mockedUser));

        //EXECUTE
        Optional<UserDTO> userDTO = this.userService.getUser("1");

        //ASSERT
        Assert.assertTrue(userDTO.isPresent());
        Assert.assertEquals(userDTO.get().getCpf(), mockedUser.getCpf());
        Assert.assertEquals(userDTO.get().getName(), mockedUser.getName());
    }

    @Test
    public void getUserNotFound() {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");

        Mockito
        .when(this.userRepository.find("1"))
        .thenReturn(Optional.of(mockedUser));

        // EXECUTE
        Optional<UserDTO> userDTO = this.userService.getUser("1234");

        // ASSERT
        Assert.assertFalse(userDTO.isPresent());
    }

    @Test
    public void createUser() {
        // MOCK
        User mockedUser = User.createUser("123", "Jhon Doe");

        Mockito
        .when(this.userRepository.save(Mockito.any()))
        .thenReturn(mockedUser);

        // EXECUTE
        CreateUserCommand createUserCommand = CreateUserCommand.newBuilder()
                .cpf("123")
                .name("Jhon Doe")
                .build();

        UserDTO createdUser = this.userService.createUser(createUserCommand);

        // ASSERT
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Assert.assertEquals(createdUser.getId(), mockedUser.getId());
        Assert.assertEquals(createdUser.getCpf(), mockedUser.getCpf());
        Assert.assertEquals(createdUser.getName(), mockedUser.getName());
    }
}