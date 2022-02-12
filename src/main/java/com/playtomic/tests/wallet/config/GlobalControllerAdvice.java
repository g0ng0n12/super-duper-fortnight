package com.playtomic.tests.wallet.config;

import com.playtomic.tests.wallet.service.stripe.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.stripe.StripeServiceException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseBody
    public ResponseEntity<?> handleEntityNotFound(final Exception ex) {
        return toResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<?> handleBadRequests(final Exception ex) {
        return toResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public ResponseEntity<?> handleServerError(final Exception ex) {
        return toResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({StripeAmountTooSmallException.class})
    @ResponseBody
    public ResponseEntity<?> handleStripeAmountTooSmallError(final Exception ex) {
        return toResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StripeServiceException.class})
    @ResponseBody
    public ResponseEntity<?> handleStripeServiceError(final Exception ex) {
        return toResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<HttpApiError> toResponseEntity(
            final Throwable throwable,
            final HttpStatus httpStatus) {
        HttpApiError error = HttpApiError.create(httpStatus, throwable.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }
}
