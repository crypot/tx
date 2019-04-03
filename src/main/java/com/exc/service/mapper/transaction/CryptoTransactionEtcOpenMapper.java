package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxEtcOpen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionEtcOpenMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxEtcOpen> {
}
