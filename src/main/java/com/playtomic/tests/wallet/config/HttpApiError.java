package com.playtomic.tests.wallet.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class HttpApiError {

    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    private HttpApiError(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static HttpApiError create(final HttpStatus status) {
        return create(status, null);
    }

    public static HttpApiError create(final HttpStatus status, final String message) {
        return new HttpApiError(status, message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}