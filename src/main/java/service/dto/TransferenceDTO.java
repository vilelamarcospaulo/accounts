package service.dto;


public class TransferenceDTO extends AccountOperationDTO {
    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
