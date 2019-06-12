package service;

import service.command.CreateUserCommand;
import service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface  UserService {
    List<UserDTO> getUsers();
    Optional<UserDTO> getUser(String id);
    UserDTO createUser(CreateUserCommand createUserCommand);
}
