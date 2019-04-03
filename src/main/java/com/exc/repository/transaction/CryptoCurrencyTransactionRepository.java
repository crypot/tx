package com.exc.repository.transaction;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.transaction.CryptoCurrencyTransaction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the CryptoCurrencyTransaction entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface CryptoCurrencyTransactionRepository<T extends CryptoCurrencyTransaction> extends JpaRepository<T, Long> {

    List<T> findByExternalId(Long externalId);

    List<T> findByOrderPairId(Long orderpairId);

    List<T> findByExternalIdIn(List<Long> externalId);

    List<T> findByExternalIdInAndStatus(List<Long> externalId, CryptoCurrencyTransactionStatus status);

    @Override
    T getOne(Long aLong);

    @Override
    Optional<T> findById(Long aLong);

    @Override
    <S extends T> S save(S entity);

}
