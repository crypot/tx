package com.exc.config;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.service.node.INode;
import com.exc.service.node.NodeTxStatusDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigInteger;

@Component
public class LocalNode implements INode {
    @Override
    public String genKey(String accId) {
        return "1";
    }

    @Override
    public String toPublic(String pk) {
        return "pubK";
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, @Nullable String details, Number value) {
        return "txId";
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, Number value) {
        return "txId";
    }

    @Override
    public BigInteger getBalance(String publicKey) {
        return new BigInteger("100");
    }

    @Override
    public BigInteger getNetFee() {
        return BigInteger.ONE;
    }

    @Override
    public NodeTxStatusDTO getStatus(String tx) {
        NodeTxStatusDTO res = new NodeTxStatusDTO();
        res.setMsg("Ok_test");
        res.setStatus(CryptoCurrencyTransactionStatus.EXECUTED);
        return res;
    }

    @Override
    public Long getTxDelay() {
        return 1l;
    }
}
