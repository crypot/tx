package com.exc.service.error;

public class NotEnoughBalanceException extends RuntimeException {
    Long userId;
    Long curId;

    public NotEnoughBalanceException(String s, Long userId, Long curId) {
        super(s);
        this.userId = userId;
        this.curId = curId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCurId() {
        return curId;
    }
}
