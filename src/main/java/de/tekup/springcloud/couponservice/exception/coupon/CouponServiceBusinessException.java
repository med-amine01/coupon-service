package de.tekup.springcloud.couponservice.exception.coupon;

public class CouponServiceBusinessException extends RuntimeException {

    public CouponServiceBusinessException(String message) {
        super(message);
    }
}
