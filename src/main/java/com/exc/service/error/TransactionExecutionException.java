package com.exc.service.error;

public class TransactionExecutionException extends RuntimeException {
    public TransactionExecutionException() {
    }

    public TransactionExecutionException(String s) {
        super(s);
    }

    public TransactionExecutionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
