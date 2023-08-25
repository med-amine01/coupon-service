package de.tekup.springcloud.couponservice.exception.coupon;

public class InvalidCouponDataException extends RuntimeException {
    public InvalidCouponDataException(String message) {
        super(message);
    }
}
