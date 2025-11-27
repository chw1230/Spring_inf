package com.example.jdbc.domain.repository.ex;

public class MyDbException extends RuntimeException {
    // 체크 예외 → 런타임 예외로 전환 (Exception Converter 역할)

    public MyDbException() {
    }

    public MyDbException(String message) {
        super(message);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }
}
