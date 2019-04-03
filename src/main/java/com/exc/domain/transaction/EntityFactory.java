package com.exc.domain.transaction;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CurrencyName;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class exists to simplify entities creation
 */
@Component
public class EntityFactory {
    public List<CryptoCurrencyTransaction> makeTxs(CurrencyName cur, CryptoCurrencyTransactionStatus status, List<CryptoCurrencyTransaction> txs) {
        return txs.stream().map(tx -> makeTx(cur, status, tx)).collect(Collectors.toList());
    }

    /**
     * create new tx of required type
     *
     * @param currencyName
     * @param status
     * @param tx     will clone fields if param not null
     * @return
     */
    public CryptoCurrencyTransaction makeTx(CurrencyName currencyName, CryptoCurrencyTransactionStatus status, @Nullable CryptoCurrencyTransaction tx) {
        CryptoCurrencyTransaction res = null;
        boolean isOpen = status.equals(CryptoCurrencyTransactionStatus.IN_PROCESS);

        switch (currencyName) {
            case ETH:
                res = isOpen ? new CryptoCurrencyTxEthOpen() : new CryptoCurrencyTxEthOther();
                break;

            case ETC:
                res = isOpen ? new CryptoCurrencyTxEtcOpen() : new CryptoCurrencyTxEtcOther();
                break;

            case BTC:
                res = isOpen ? new CryptoCurrencyTxBtcOpen() : new CryptoCurrencyTxBtcOther();
                break;
        }
        if (tx != null) {
            res.setValue(tx.getValue());
            res.setMessage(tx.getMessage());
            res.setCreateDate(tx.getCreateDate());
            res.setStatus(tx.getStatus());
            res.setTx(tx.getTx());
            res.setExternalId(tx.getExternalId());
            res.setType(tx.getType());
        }

        return res;
    }

}
