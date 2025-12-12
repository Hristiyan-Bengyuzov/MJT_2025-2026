package bg.sofia.uni.fmi.mjt.order.domain;

public enum Category {
    ELECTRONICS, CLOTHING, FOOTWEAR, HOME_APPLIANCES, BOOKS;

    public static Category of(String str) {
        return Category.valueOf(str.trim().toUpperCase().replace(' ', '_'));
    }
}