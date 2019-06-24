package datastore.impl;

import datastore.UserRepository;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class UserRepositoryImplTest {
    @Before
    public void setup() {
        UserRepositoryImpl.initializeInstance();
    }

    @Test
    public void save() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        User user = User.createUser("123", "Jhon Doe");

        user = userRepository.save(user);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void find() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        User user = this.saveTestUser();

        Optional<User> getUser = userRepository.find(user.getId());
        Assert.assertTrue(getUser.isPresent());
        Assert.assertEquals(getUser.get().getCpf(), "123");
        Assert.assertEquals(getUser.get().getName(), "Jhon Doe");
    }

    @Test
    public void findNoUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        Optional<User> getUser = userRepository.find("123");
        Assert.assertFalse(getUser.isPresent());
    }

    @Test
    public void deleteUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        User user = this.saveTestUser();

        User deletedUser = userRepository.delete(user.getId());
        Assert.assertEquals(user, deletedUser);
    }

    @Test
    public void deleteNotExistentUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        User deletedUser = userRepository.delete("123");
        Assert.assertNull(deletedUser);
    }

    @Test
    public void findAll() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        List<User> users = Arrays.asList(this.saveTestUser(), this.saveTestUser());
        List<User> usersFromBase = userRepository.getAll();

        Assert.assertEquals(users.size(), usersFromBase.size());
        IntStream.range(0, users.size()).forEach(i -> Assert.assertEquals(users.get(i), usersFromBase.get(i)));
    }


    private User saveTestUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        User user = User.createUser("123", "Jhon Doe");
        user = userRepository.save(user);

        return user;
    }
}