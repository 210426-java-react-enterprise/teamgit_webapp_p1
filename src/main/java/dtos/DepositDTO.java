package dtos;

public class DepositDTO {

    private double deposit;

    public DepositDTO(){
        super();
    }

    public DepositDTO(double deposit) {
        this.deposit = deposit;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
