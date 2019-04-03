package com.exc.service.mapper.transaction;


import com.exc.domain.transaction.CryptoCurrencyTransaction;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.mapper.EntityMapper;

import java.util.List;

public interface CryptoTransactionEntityMapper<E extends CryptoCurrencyTransaction> extends EntityMapper<CryptoCurrencyTransactionDTO, E> {
    E fromId(Long id);

    @Override
    E toEntity(CryptoCurrencyTransactionDTO dto);

    @Override
    CryptoCurrencyTransactionDTO toDto(E entity);

    @Override
    List<E> toEntity(List<CryptoCurrencyTransactionDTO> dtoList);

    @Override
    List<CryptoCurrencyTransactionDTO> toDto(List<E> entityList);
}
