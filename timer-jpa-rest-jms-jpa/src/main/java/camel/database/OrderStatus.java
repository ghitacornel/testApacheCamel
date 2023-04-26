package camel.database;

public enum OrderStatus {

    NEW,
    TRY_FOR_VOUCHER,
    VOUCHER_COMPLETED,
    TRY_FOR_PAYMENT,
    PAYMENT_COMPLETED,
    PROCESSED,
    FAILED

}
