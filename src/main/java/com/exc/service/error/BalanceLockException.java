package com.exc.service.error;

public class BalanceLockException extends RuntimeException {
    public BalanceLockException(String s) {
        super(s);
    }
}
