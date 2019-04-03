package com.exc.service.node;

import javax.annotation.Nullable;
import java.math.BigInteger;

/**
 * Basic interface between different blockchain
 */
public interface INode {
    String genKey(String accId);

    String toPublic(String pk);

    String transfer(String toPublic, String fromPrivate, @Nullable String details, Number value);

    String transfer(String toPublic, String fromPrivate, Number value);

    BigInteger getBalance(String publicKey);

    BigInteger getNetFee();

    NodeTxStatusDTO getStatus(String tx);

    Long getTxDelay();



}
