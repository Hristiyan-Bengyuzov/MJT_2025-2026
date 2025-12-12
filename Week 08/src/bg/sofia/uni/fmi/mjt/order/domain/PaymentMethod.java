package bg.sofia.uni.fmi.mjt.order.domain;

public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, PAYPAL, AMAZON_PAY, GIFT_CARD;

    public static PaymentMethod of(String str) {
        return PaymentMethod.valueOf(str.trim().toUpperCase().replace(' ', '_'));
    }
}