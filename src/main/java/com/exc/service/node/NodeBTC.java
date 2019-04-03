package com.exc.service.node;

import com.exc.config.ApplicationProperties;
import com.exc.service.EncryptDecrypt;
import io.github.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;

@Component
@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
public class NodeBTC extends ANode {

    BitcoindRpcClient rpcClient;

    protected NodeBTC(EncryptDecrypt encryptDecrypt, ApplicationProperties applicationProperties) {
        super(encryptDecrypt, applicationProperties);
    }


    public void init() {
        try {
            rpcClient = new BitcoinJSONRPCClient(applicationProperties.getUrl("btc"));
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public String genKey(String acc) {
        String addr = rpcClient.getNewAddress(acc);
        return encryptDecrypt.encrypt(rpcClient.dumpPrivKey(addr));
    }

    @Override
    public String toPublic(String pk) {
        return rpcClient.getAccountAddress(pk);
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, @Nullable String details, Number value) {
        String tx = null;
        if (details != null) {
            String[] arr = details.split(",");
            if (arr.length == 2) {
                tx = rpcClient.sendFrom(encryptDecrypt.decrypt(fromPrivate), toPublic, value.doubleValue(), 6, arr[0], arr[1]);
            } else {
                tx = rpcClient.sendFrom(encryptDecrypt.decrypt(fromPrivate), toPublic, value.doubleValue(), 6, arr[0]);
            }
        }
        return tx;
    }

    @Override
    public String transfer(String toPublic, String fromPrivate, Number value) {
        return transfer(toPublic, fromPrivate, null, value);
    }

    @Override
    public BigInteger getBalance(String publicKey) {
        double rs = rpcClient.getBalance(publicKey);
        //todo: convert
        return new BigDecimal(rs).toBigInteger();
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
