package com.exc.service.mapper.transaction;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CurrencyName;
import org.springframework.stereotype.Component;

@Component
public class CryptoTransactionMapperFactory {
    private final CryptoTransactionBtcOpenMapper cryptoTransactionBtcOpenMapper;
    private final CryptoTransactionEtcOpenMapper cryptoTransactionEtcOpenMapper;
    private final CryptoTransactionEthOpenMapper cryptoTransactionEthOpenMapper;
    private final CryptoTransactionBtcOtherMapper cryptoTransactionBtcOtherMapper;
    private final CryptoTransactionEtcOtherMapper cryptoTransactionEtcOtherMapper;
    private final CryptoTransactionEthOtherMapper cryptoTransactionEthOtherMapper;

    public CryptoTransactionMapperFactory(CryptoTransactionBtcOpenMapper cryptoTransactionBtcOpenMapper, CryptoTransactionEtcOpenMapper cryptoTransactionEtcOpenMapper, CryptoTransactionEthOpenMapper cryptoTransactionEthOpenMapper, CryptoTransactionBtcOtherMapper cryptoTransactionBtcOtherMapper, CryptoTransactionEtcOtherMapper cryptoTransactionEtcOtherMapper, CryptoTransactionEthOtherMapper cryptoTransactionEthOtherMapper) {
        this.cryptoTransactionBtcOpenMapper = cryptoTransactionBtcOpenMapper;
        this.cryptoTransactionEtcOpenMapper = cryptoTransactionEtcOpenMapper;
        this.cryptoTransactionEthOpenMapper = cryptoTransactionEthOpenMapper;
        this.cryptoTransactionBtcOtherMapper = cryptoTransactionBtcOtherMapper;
        this.cryptoTransactionEtcOtherMapper = cryptoTransactionEtcOtherMapper;
        this.cryptoTransactionEthOtherMapper = cryptoTransactionEthOtherMapper;
    }

    public CryptoTransactionEntityMapper getMapper(CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        CryptoTransactionEntityMapper res = null;
        boolean isOpen = status.equals(CryptoCurrencyTransactionStatus.IN_PROCESS);
        switch (currencyName) {
            case ETH:
                res = isOpen ? cryptoTransactionEthOpenMapper : cryptoTransactionEthOtherMapper;
                break;
            case ETC:
                res = isOpen ? cryptoTransactionEtcOpenMapper : cryptoTransactionEtcOtherMapper;
                break;
            case BTC:
                res = isOpen ? cryptoTransactionBtcOpenMapper : cryptoTransactionBtcOtherMapper;
                break;
        }
        return res;
    }
}
