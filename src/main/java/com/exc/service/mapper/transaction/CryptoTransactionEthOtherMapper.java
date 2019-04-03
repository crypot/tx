package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxEthOther;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionEthOtherMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxEthOther> {
}
