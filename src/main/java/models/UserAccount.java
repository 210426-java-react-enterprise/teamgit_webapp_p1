package models;

import annotations.*;

@Entity
@Table(
        name = "accounts"
)
public class UserAccount {
    @Id(
            name = "account_num"
    )
    @Column(
            name = "account_num",
            nullable = false,
            unique = true,
            type = "serial",
            updateable = false
    )
    private int account_num;
    @ForeignKey(
            name = "user_id",
            references = "users"
    )
    @Column(
            name = "user_id",
            nullable = false,
            unique = true,
            type = "int",
            updateable = false
    )
    private int id;
    @Column(
            name = "balance",
            nullable = false,
            unique = false,
            type = "double",
            length = "12,2",
            updateable = true
    )
    private double balance;

    public UserAccount(){

    }

    @Getter
    public int getAccount_num() {
        return account_num;
    }

    @Setter(
            name = "account_num"
    )
    public void setAccount_num(int account_num) {
        this.account_num = account_num;
    }

    @Getter
    public int getId() {
        return id;
    }

    @Setter(
            name = "id"
    )
    public void setId(int id) {
        this.id = id;
    }

    @Getter
    public double getBalance() {
        return this.balance;
    }

    @Setter(
            name = "balance"
    )
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserAccount(int account_num,int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.account_num = account_num;
    }
}
