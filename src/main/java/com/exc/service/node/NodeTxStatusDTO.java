package com.exc.service.node;


import com.exc.domain.CryptoCurrencyTransactionStatus;

import java.io.Serializable;

/**
 * contains detailed generalized tx info from crypto nodes, uses in TxMonitorService
 */
public class NodeTxStatusDTO implements Serializable {
    private CryptoCurrencyTransactionStatus status;
    private String msg;

    public CryptoCurrencyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(CryptoCurrencyTransactionStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
