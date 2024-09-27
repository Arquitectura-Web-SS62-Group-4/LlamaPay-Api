package com.wisecoin.LlamaPay_Api.exceptions;

public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String message) {
        super(message);
    }
}
