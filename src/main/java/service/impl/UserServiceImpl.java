package service.impl;

import datastore.UserRepository;
import domain.User;
import org.mapstruct.factory.Mappers;
import service.UserService;
import service.command.CreateUserCommand;
import service.dto.UserDTO;
import service.mappers.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    public List<UserDTO> getUsers() {
        return this.userRepository.getAll().stream().map(this.userMapper::domainToDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUser(String id) {
        return this.userRepository.find(id).map(this.userMapper::domainToDTO);
    }

    public UserDTO createUser(CreateUserCommand createUserCommand) {
        User domainUser = User.createUser(createUserCommand.getCpf(), createUserCommand.getName());
        return this.userMapper.domainToDTO(this.userRepository.save(domainUser));
    }
}
