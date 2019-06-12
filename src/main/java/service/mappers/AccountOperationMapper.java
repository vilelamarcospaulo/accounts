package service.mappers;

import domain.accountoperations.Deposit;
import domain.accountoperations.Transference;
import domain.accountoperations.Withdraw;
import org.mapstruct.Mapper;
import service.dto.DepositDTO;
import service.dto.TransferenceDTO;
import service.dto.WithdrawDTO;

@Mapper
public interface AccountOperationMapper {
    TransferenceDTO tranferenceToDTO(Transference tranference);
    DepositDTO depositToDTO(Deposit deposit);
    WithdrawDTO withdrawToDTO(Withdraw withdraw);
}
