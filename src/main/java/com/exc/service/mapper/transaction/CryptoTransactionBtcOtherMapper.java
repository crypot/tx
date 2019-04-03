package com.exc.service.mapper.transaction;

import com.exc.domain.transaction.CryptoCurrencyTxBtcOther;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CryptoTransactionBtcOtherMapper extends CryptoTransactionEntityMapper<CryptoCurrencyTxBtcOther> {
}
