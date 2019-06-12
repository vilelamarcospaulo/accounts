package datastore.impl;

import domain.User;
import org.junit.Test;
import org.junit.Assert;

import java.util.Optional;

public class UserRepositoryImplTest {
    @Test
    public void save() {
        User user = User.createUser("123", "Jhon Doe");

        user = UserRepositoryImpl.getInstance().save(user);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        Optional<User> getUser = UserRepositoryImpl.getInstance().find(user.getId());
        Assert.assertTrue(getUser.isPresent());
    }
}