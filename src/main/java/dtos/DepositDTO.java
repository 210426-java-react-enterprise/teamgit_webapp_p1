package dtos;

public class DepositDTO {

    private double deposit_am;

    public DepositDTO(double deposit_am) {
        this.deposit_am = deposit_am;
    }

    public double getDeposit_am() {
        return deposit_am;
    }

    public void setDeposit_am(double deposit_am) {
        this.deposit_am = deposit_am;
    }
}
