package service.mappers;

import domain.Account;
import org.mapstruct.Mapper;
import service.dto.AccountDTO;

@Mapper
public interface AccountMapper {
    AccountDTO domainToDto(Account account);

}
