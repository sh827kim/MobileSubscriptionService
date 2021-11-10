package com.spark.toy.exception;

public class CustomNoSuchElementException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 데이터입니다.";
    public CustomNoSuchElementException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }
    public CustomNoSuchElementException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
