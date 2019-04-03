package com.exc.service.node;

import com.exc.config.ApplicationProperties;
import com.exc.service.EncryptDecrypt;
import io.github.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Component
@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
public class NodeETH extends ANode {

    protected Web3j web3;

    protected NodeETH(EncryptDecrypt encryptDecrypt, ApplicationProperties applicationProperties) {
        super(encryptDecrypt, applicationProperties);
    }

    public void init() {
        web3 = Web3j.build(new HttpService(applicationProperties.getUrl("eth")));
    }

    @Override
    public String genKey(String acc) {
        ECKeyPair pair = null;
        try {
            pair = Keys.createEcKeyPair();
        } catch (Exception e) {
            log.error("Cant generate pkeys");
        }
        // String addr = Keys.getAddress(pair);
        String pk = Numeric.toHexStringWithPrefixZeroPadded(pair.getPrivateKey(), Keys.PRIVATE_KEY_LENGTH_IN_HEX);
        return encryptDecrypt.encrypt(pk);
    }

    @Override
    public String toPublic(String pk) {
        ECKeyPair pair = ECKeyPair.create(Numeric.toBigInt(encryptDecrypt.decrypt(pk)));
        return Keys.getAddress(pair);
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, @Nullable String details, Number value) {
        ECKeyPair pair = ECKeyPair.create(Numeric.toBigInt(encryptDecrypt.decrypt(fromPrivate)));

        TransactionReceipt transactionReceipt = null;
        BigDecimal val = new BigDecimal((BigInteger) value);
        try {
            transactionReceipt = Transfer.sendFunds(
                web3,
                Credentials.create(pair),
                toPublic,
                val,
                Convert.Unit.WEI)
                .send();
        } catch (Exception e) {
            log.error("Error when sending transaction ", e);
        }

        return transactionReceipt.getTransactionHash();
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, Number value) {
        return transfer(toPublic, fromPrivate, null, value);
    }

    @Override
    public BigInteger getBalance(String publicKey) {
        EthGetBalance
            balance = null;
        try {
            balance = web3.ethGetBalance(publicKey, DefaultBlockParameterName.LATEST).send();
        } catch (IOException e) {
            log.error("Can't retrieve balance {}", publicKey, e);
        }
        return balance.getBalance();
    }

    @Override
    public NodeTxStatusDTO getStatus(String tx) {
        return null;
    }

    @Override
    public Long getTxDelay() {
        return 10l;
    }
}
