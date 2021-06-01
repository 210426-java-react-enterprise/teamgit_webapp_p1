package models;

import annotations.*;

import java.sql.Timestamp;

@Entity
@Table(
        name = "transactions"
)
public class TransactionValues {
    @Id(
            name = "trans_id"
    )
    @Column(
            name = "trans_id",
            type = "serial",
            nullable = false,
            unique = true,
            updateable = false
    )
    private int trans_id;
    @ForeignKey(
            name = "account_id",
            references = "accounts"
    )
    @Column(
            name = "account_id",
            type = "int",
            nullable = false,
            unique = false,
            updateable = false
    )
    private int account_id;

    @Column(
            name = "prev_bal",
            type = "double",
            nullable = false,
            unique = false,
            length = "12,2",
            updateable = false
    )
    private double prev_bal;

    @Column(
            name = "change",
            type = "double",
            nullable = false,
            unique = false,
            length = "12,2",
            updateable = false
    )
    private double change;
    @Column(
            name = "timestamp",
            type = "timestamp",
            nullable = false,
            unique = false,
            updateable = false
    )
    private static Timestamp timestamp;

    public TransactionValues(){

    }

    public TransactionValues(int trans_id, int account_id, double prev_bal, double change) {
        this.trans_id = trans_id;
        this.account_id = account_id;
        this.prev_bal = prev_bal;
        this.change = change;
    }

    @Getter
    public int getTrans_id() {
        return trans_id;
    }

    @Getter
    public int getAccount_id() {
        return account_id;
    }

    @Getter
    public double getPrev_bal() {
        return prev_bal;
    }

    @Getter
    public double getChange() {
        return change;
    }

    @Getter
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Setter(
            name = "trans_id"
    )
    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }

    @Setter(
            name = "account_id"
    )
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Setter(
            name = "prev_bal"
    )
    public void setPrev_bal(double prev_bal) {
        this.prev_bal = prev_bal;
    }

    @Setter(
            name = "change"
    )
    public void setChange(double change) {
        this.change = change;
    }
}


