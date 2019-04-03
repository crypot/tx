package com.exc.repository.transaction;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CurrencyName;
import com.exc.domain.transaction.CryptoCurrencyTransaction;
import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyTransactionRepositoryFactory {

    private final CryptoTransactionRepositoryEthOpen cryptoTransactionRepositoryEthOpen;
    private final CryptoTransactionRepositoryBtcOpen cryptoTransactionRepositoryBtcOpen;
    private final CryptoTransactionRepositoryEtcOpen cryptoTransactionRepositoryEtcOpen;
    private final CryptoTransactionRepositoryEthOther cryptoTransactionRepositoryEthOther;
    private final CryptoTransactionRepositoryBtcOther cryptoTransactionRepositoryBtcOther;
    private final CryptoTransactionRepositoryEtcOther cryptoTransactionRepositoryEtcOther;

    public CryptoCurrencyTransactionRepositoryFactory(CryptoTransactionRepositoryEthOpen cryptoTransactionRepositoryEthOpen, CryptoTransactionRepositoryBtcOpen cryptoTransactionRepositoryBtcOpen, CryptoTransactionRepositoryEtcOpen cryptoTransactionRepositoryEtcOpen, CryptoTransactionRepositoryEthOther cryptoTransactionRepositoryEthOther, CryptoTransactionRepositoryBtcOther cryptoTransactionRepositoryBtcOther, CryptoTransactionRepositoryEtcOther cryptoTransactionRepositoryEtcOther) {
        this.cryptoTransactionRepositoryEthOpen = cryptoTransactionRepositoryEthOpen;
        this.cryptoTransactionRepositoryBtcOpen = cryptoTransactionRepositoryBtcOpen;
        this.cryptoTransactionRepositoryEtcOpen = cryptoTransactionRepositoryEtcOpen;
        this.cryptoTransactionRepositoryEthOther = cryptoTransactionRepositoryEthOther;
        this.cryptoTransactionRepositoryBtcOther = cryptoTransactionRepositoryBtcOther;
        this.cryptoTransactionRepositoryEtcOther = cryptoTransactionRepositoryEtcOther;
    }

    public CryptoCurrencyTransactionRepository<CryptoCurrencyTransaction> getRepository(CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        CryptoCurrencyTransactionRepository res = null;
        boolean isOpen = status.equals(CryptoCurrencyTransactionStatus.IN_PROCESS);
        switch (currencyName) {
            case ETH:
                res = isOpen ? cryptoTransactionRepositoryEthOpen : cryptoTransactionRepositoryEthOther;
                break;
            case ETC:
                res = isOpen ? cryptoTransactionRepositoryEtcOpen : cryptoTransactionRepositoryEtcOther;
                break;
            case BTC:
                res = isOpen ? cryptoTransactionRepositoryBtcOpen : cryptoTransactionRepositoryBtcOther;
                break;

        }

        return res;
    }
}
