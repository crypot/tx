package com.exc.service.node;

import com.exc.config.ApplicationProperties;
import com.exc.service.EncryptDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

public abstract class ANode implements INode {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final EncryptDecrypt encryptDecrypt;
    protected final ApplicationProperties applicationProperties;

    protected ANode(EncryptDecrypt encryptDecrypt, ApplicationProperties applicationProperties) {
        this.encryptDecrypt = encryptDecrypt;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public BigInteger getNetFee() {
        //todo return real TX fee
        return BigInteger.ONE;
    }

    @PostConstruct
    protected abstract void init();
}
