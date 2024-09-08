package com.wisecoin.LlamaPay_Api.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(){
        super();
    }

    public ValidationException(String message){
        super(message);
    }
}
