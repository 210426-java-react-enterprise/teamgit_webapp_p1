package models;

import annotations.Column;
import annotations.Connection;
import annotations.Entity;
import annotations.ForeignKey;
import annotations.Id;
import annotations.Setter;
import annotations.Table;

@Entity
@Connection(
        url = "jdbc:postgresql://project0-accounts.comfkmj3hfze.us-west-1.rds.amazonaws.com:5432/postgres?currentSchema=public",
        username = "thomas",
        password = "revature"
)
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
    private static int trans_id;
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
    private static int account_id;
    @Column(
            name = "prev_bal",
            type = "double",
            nullable = false,
            unique = false,
            length = "12,2",
            updateable = false
    )
    private static double prev_bal;
    @Column(
            name = "change",
            type = "double",
            nullable = false,
            unique = false,
            length = "12,2",
            updateable = false
    )
    private static double change;
    @Column(
            name = "timestamp",
            type = "timestamp",
            nullable = false,
            unique = false,
            updateable = false
    )
    private static String timestamp;

    public TransactionValues() {
    }

    @Setter(
            name = "trans_id"
    )
    public static void setTrans_id(int trans_id) {
        TransactionValues.trans_id = trans_id;
    }

    @Setter(
            name = "account_id"
    )
    public static void setAccount_id(int account_id) {
        TransactionValues.account_id = account_id;
    }

    @Setter(
            name = "prev_bal"
    )
    public static void setPrev_bal(double prev_bal) {
        TransactionValues.prev_bal = prev_bal;
    }

    @Setter(
            name = "change"
    )
    public static void setChange(double change) {
        TransactionValues.change = change;
    }

    @Setter(
            name = "timestamp"
    )
    public static void setTimestamp(String timestamp) {
        TransactionValues.timestamp = timestamp;
    }
}
