package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxEtcOther;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionEtcOtherMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxEtcOther> {
}
