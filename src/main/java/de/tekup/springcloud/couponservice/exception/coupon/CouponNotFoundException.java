package de.tekup.springcloud.couponservice.exception.coupon;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String message) {
        super(message);
    }
}
