package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxBtcOpen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionBtcOpenMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxBtcOpen> {
}
