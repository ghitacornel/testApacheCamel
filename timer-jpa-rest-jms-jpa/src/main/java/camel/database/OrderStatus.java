package camel.database;

public enum OrderStatus {

    NEW,
    TRY_FOR_VOUCHER_PERCENTAGE,
    VOUCHER_PERCENTAGE_COMPLETED,
    TRY_FOR_PAYMENT,
    PAYMENT_COMPLETE,
    PROCESSED,
    FAILED

}
