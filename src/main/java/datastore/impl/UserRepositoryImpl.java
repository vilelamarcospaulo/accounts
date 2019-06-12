package datastore.impl;

import datastore.UserRepository;
import domain.User;


public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {
    private static final UserRepository userRepository = new UserRepositoryImpl();

    private UserRepositoryImpl() {}

    public static UserRepository getInstance() { return UserRepositoryImpl.userRepository; }
}
