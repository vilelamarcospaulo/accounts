package service.mappers;


import org.mapstruct.Mapper;
import service.dto.UserDTO;
import domain.User;

@Mapper
public interface UserMapper {
    UserDTO domainToDTO(User user);
}
