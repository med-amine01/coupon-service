package de.tekup.springcloud.couponservice.exception;

public class CouponAlreadyExistsException extends RuntimeException {
    public CouponAlreadyExistsException(String message) {
        super(message);
    }
}
