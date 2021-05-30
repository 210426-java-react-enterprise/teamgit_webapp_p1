package dtos;

public class WithdrawDTO {

    private double withdraw_am;

    public WithdrawDTO(double withdraw_am) {
        this.withdraw_am = withdraw_am;
    }

    public double getWithdraw_am() {
        return withdraw_am;
    }

    public void setWithdraw_am(double withdraw_am) {
        this.withdraw_am = withdraw_am;
    }
}
