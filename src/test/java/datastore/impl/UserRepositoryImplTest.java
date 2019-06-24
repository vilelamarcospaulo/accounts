package datastore.impl;

import datastore.UserRepository;
import domain.User;
import org.junit.Test;
import org.junit.Assert;

import java.util.Optional;

public class UserRepositoryImplTest {
    @Test
    public void save() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();

        User user = User.createUser("123", "Jhon Doe");

        user = userRepository.save(user);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        Optional<User> getUser = userRepository.find(user.getId());
        Assert.assertTrue(getUser.isPresent());
    }
}