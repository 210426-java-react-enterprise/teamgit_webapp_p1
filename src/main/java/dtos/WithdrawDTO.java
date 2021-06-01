package dtos;

public class WithdrawDTO {

    private double withdraw;

    public WithdrawDTO(){
        super();
    }

    public WithdrawDTO(double withdraw) {
        this.withdraw = withdraw;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }
}
