package dtos;

public class DepositDTO {

    private String deposit;

    public DepositDTO(){
        super();
    }

    public DepositDTO(String deposit) {
        this.deposit = deposit;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
