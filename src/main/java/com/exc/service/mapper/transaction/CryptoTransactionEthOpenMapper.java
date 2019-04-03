package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxEthOpen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionEthOpenMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxEthOpen> {
}
