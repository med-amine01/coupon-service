package de.tekup.springcloud.couponservice.exception;

import de.tekup.springcloud.couponservice.exception.coupon.CouponAlreadyExistsException;
import de.tekup.springcloud.couponservice.exception.coupon.CouponNotFoundException;
import de.tekup.springcloud.couponservice.exception.coupon.InvalidCouponDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Coupon already exits
    @ExceptionHandler(CouponAlreadyExistsException.class)
    public ResponseEntity<?> handleCouponAlreadyExistsException(CouponAlreadyExistsException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle Coupon Not Found
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<?> handleCouponNotFoundException(CouponNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle Invalid Coupon Data
    @ExceptionHandler(InvalidCouponDataException.class)
    public ResponseEntity<?> handleInvalidCouponDataException(InvalidCouponDataException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handling custom validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
