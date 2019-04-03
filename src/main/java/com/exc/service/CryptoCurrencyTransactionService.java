package com.exc.service;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CryptoCurrencyTransactionType;
import com.exc.domain.CurrencyName;
import com.exc.domain.transaction.CryptoCurrencyTransaction;
import com.exc.domain.transaction.EntityFactory;
import com.exc.repository.transaction.CryptoCurrencyTransactionRepository;
import com.exc.repository.transaction.CryptoCurrencyTransactionRepositoryFactory;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.dto.PreparedExDTO;
import com.exc.service.error.TransactionExecutionException;
import com.exc.service.mapper.transaction.CryptoTransactionEntityMapper;
import com.exc.service.mapper.transaction.CryptoTransactionMapperFactory;
import com.exc.service.node.INode;
import com.exc.service.node.NodeAdapterFactory;
import com.exc.service.node.NodeTxStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * private
 * Service Implementation for managing CryptoCurrencyTransaction.
 */
@Service
@Transactional
public class CryptoCurrencyTransactionService {

    private final Logger log = LoggerFactory.getLogger(CryptoCurrencyTransactionService.class);
    private CryptoCurrencyTransactionRepositoryFactory cryptoTransactionRepositoryFactory;
    private CryptoTransactionMapperFactory cryptoCurrencyTransactionMapperFactory;
    private NodeAdapterFactory nodeAdapterFactory;
    private EntityFactory currencyEntityFactory;
    private TxMonitorService txMonitorService;

    public CryptoCurrencyTransactionService(CryptoCurrencyTransactionRepositoryFactory cryptoTransactionRepositoryFactory, CryptoTransactionMapperFactory cryptoCurrencyTransactionMapperFactory, NodeAdapterFactory nodeAdapterFactory, EntityFactory currencyEntityFactory) {
        this.cryptoTransactionRepositoryFactory = cryptoTransactionRepositoryFactory;
        this.cryptoCurrencyTransactionMapperFactory = cryptoCurrencyTransactionMapperFactory;
        this.nodeAdapterFactory = nodeAdapterFactory;
        this.currencyEntityFactory = currencyEntityFactory;
    }


    @Autowired
    public void setTxMonitorService(@Lazy TxMonitorService txMonitorService) {
        this.txMonitorService = txMonitorService;
    }
    /*    @Autowired
    private TxMonitorService txMonitorService;*/



    /*   *//**
     * Save a cryptoCurrencyTransaction.
     *
     * @param cryptoCurrencyTransactionDTO the entity to save
     * @return the persisted entity
     *//*
    public CryptoCurrencyTransactionDTO save(CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO) {
        log.debug("Request to save CryptoCurrencyTransaction : {}", cryptoCurrencyTransactionDTO);
        CryptoCurrencyTransaction cryptoCurrencyTransaction = cryptoCurrencyTransactionMapperFactory.toEntity(cryptoCurrencyTransactionDTO);

        cryptoCurrencyTransaction = cryptoTransactionRepositoryFactory
            .getRepository(currencyPairRepository.findOne(cryptoCurrencyTransaction.getOrderPair().getPair().getId()))
            .save(cryptoCurrencyTransaction);

        return cryptoCurrencyTransactionMapperFactory.toDto(cryptoCurrencyTransaction);
    }*/

    /**
     * Get all the cryptoCurrencyTransactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CryptoCurrencyTransactionDTO> findAll(Pageable pageable, CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        log.debug("Request to get all CryptoCurrencyTransactions");
        CryptoTransactionEntityMapper mapper = cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, status);

        return cryptoTransactionRepositoryFactory.getRepository(currencyName, status)
            .findAll(pageable)
            .map(mapper::toDto);
    }


    @Transactional(readOnly = true)
    public List<CryptoCurrencyTransactionDTO> findByExternalId(Long externalId, CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        log.debug("Request to get by external id : {}", externalId);
        CryptoTransactionEntityMapper mapper = cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, status);
        return cryptoTransactionRepositoryFactory
            .getRepository(currencyName, status)
            .findByExternalId(externalId)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CryptoCurrencyTransactionDTO> findByExternalId(List<Long> externalId, CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        log.debug("Request to get by external id : {}", externalId);
        CryptoTransactionEntityMapper mapper = cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, status);
        return cryptoTransactionRepositoryFactory
            .getRepository(currencyName, status)
            .findByExternalIdInAndStatus(externalId, status)
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CryptoCurrencyTransactionDTO> findActiveByMainOrderId(Long mainOrderId, CurrencyName buy, CurrencyName sell) {
        log.debug("Request to fetch by main order id: {}", mainOrderId);
        List<CryptoCurrencyTransactionDTO> res = new ArrayList<>();

        CryptoCurrencyTransactionStatus status = CryptoCurrencyTransactionStatus.IN_PROCESS;
        CryptoTransactionEntityMapper buyMapper = cryptoCurrencyTransactionMapperFactory.getMapper(buy, status);
        CryptoTransactionEntityMapper sellMapper = cryptoCurrencyTransactionMapperFactory.getMapper(sell, status);

        res.addAll(buyMapper.toDto(cryptoTransactionRepositoryFactory.getRepository(buy, status).findByOrderPairId(mainOrderId)));
        res.addAll(sellMapper.toDto(cryptoTransactionRepositoryFactory.getRepository(sell, status).findByOrderPairId(mainOrderId)));

        return res;

    }

    /**
     * Get one cryptoCurrencyTransaction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CryptoCurrencyTransactionDTO findOne(Long id, CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        log.debug("Request to get CryptoCurrencyTransaction : {}", id);
        CryptoTransactionEntityMapper mapper = cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, status);

        CryptoCurrencyTransaction cryptoCurrencyTransaction = cryptoTransactionRepositoryFactory
            .getRepository(currencyName, status)
            .getOne(id);
        return mapper.toDto(cryptoCurrencyTransaction);
    }

    public void updateTxStatus(CurrencyName currencyName, Long txId, NodeTxStatusDTO nodeTxStatusDTO) {

        CryptoCurrencyTransactionRepository processRepo = cryptoTransactionRepositoryFactory
            .getRepository(currencyName, CryptoCurrencyTransactionStatus.IN_PROCESS);

        CryptoCurrencyTransaction tx = processRepo.getOne(txId);
        tx.setMessage(nodeTxStatusDTO.getMsg());
        tx.setStatus(nodeTxStatusDTO.getStatus());
        processRepo.save(tx);
    }

    /**
     * need to keep track orders table migrations.
     *
     * @param currencyName
     * @param oldId        external entity ID
     * @param newId        external entity ID
     * @return migrated tx
     */
    public List<CryptoCurrencyTransactionDTO> migrateTx(CurrencyName currencyName, Long oldId, Long newId) {
        CryptoCurrencyTransactionRepository processRepo = cryptoTransactionRepositoryFactory
            .getRepository(currencyName, CryptoCurrencyTransactionStatus.IN_PROCESS);
        log.info("migrating tx for old orderID {}|newID {}, currency {}", oldId, newId, currencyName);

        List<CryptoCurrencyTransaction> oldTxs = processRepo.findByExternalId(oldId);
        if (oldTxs.size() == 0) {
            return Collections.EMPTY_LIST;
            //log.error("Cant find transactions for processed order Id:{}/{}", oldId, newId);
        }

        List<CryptoCurrencyTransaction> newTxs = currencyEntityFactory.makeTxs(currencyName, CryptoCurrencyTransactionStatus.EXECUTED, oldTxs);

        processRepo.delete(oldTxs);
        CryptoCurrencyTransactionRepository exRepo = cryptoTransactionRepositoryFactory.getRepository(currencyName, CryptoCurrencyTransactionStatus.EXECUTED);
        newTxs.forEach(tx -> tx.setExternalId(newId));
        newTxs = exRepo.saveAll(newTxs);


        CryptoTransactionEntityMapper mapper = cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, CryptoCurrencyTransactionStatus.EXECUTED);
        return newTxs.stream().map(mapper::toDto).collect(Collectors.toList());
        //until there is one id sequence per currency, there should  be no conflict with different order type;
    }

    /**
     * Process executed order Txs in batch.
     *
     * @param
     */
    public void processOrderTransactions(List<PreparedExDTO> preparedTxs) {
        Long mainOrderId = getMainOrderId(preparedTxs);
        CurrencyName buy = preparedTxs.get(0).getCurIdBuy(), sell = preparedTxs.get(0).getCurIdSell();
        log.info("Main order id : {}", mainOrderId);
        for (PreparedExDTO pex : preparedTxs) {
            processOrderTx(pex.getCurIdBuy(), pex.getToPublicBuy(), pex.getFromPrivateBuy(), pex.getValueBuy(), pex.getExternalIdBuy(), mainOrderId);
            processOrderTx(pex.getCurIdSell(), pex.getToPublicSell(), pex.getFromPrivateSell(), pex.getValueSell(), pex.getExternalIdSell(), mainOrderId).getId();
        }
        txMonitorService.monitorOrderTx(mainOrderId, buy, sell);

    }

    private Long getMainOrderId(List<PreparedExDTO> preparedTxs) {
        boolean isBuy = preparedTxs.stream().filter(pp -> pp.getExternalIdBuy() == preparedTxs.get(0).getExternalIdBuy()).count() == preparedTxs.size(), isSell = false;

        if (!isBuy) {
            isSell = preparedTxs.stream().filter(pp -> pp.getExternalIdSell() == preparedTxs.get(0).getExternalIdSell()).count() == preparedTxs.size();
        }
        if (!isBuy && !isSell) {
            log.error("Not able to process order transactions. Failed to backtrack main order Id. First order buy Id {}", preparedTxs.get(0).getExternalIdBuy());
            throw new TransactionExecutionException("Failed to backtrack main order!");
        }
        return isBuy ? preparedTxs.get(0).getExternalIdBuy() : preparedTxs.get(0).getExternalIdSell();
    }

    /**
     * Will create transaction, and submit it to the external node
     *
     * @param currencyName
     * @param toPublic
     * @param fromPrivate
     * @param value
     * @param externalId
     * @return
     */


    private CryptoCurrencyTransactionDTO processOrderTx(CurrencyName currencyName, String toPublic, String fromPrivate, BigInteger value, Long externalId, Long mainOrderId) {
        return process(currencyName, toPublic, fromPrivate, value, CryptoCurrencyTransactionType.ORDER, externalId, mainOrderId);

    }

    public CryptoCurrencyTransactionDTO process(CurrencyName currencyName, String toPublic, String fromPrivate, BigInteger value, CryptoCurrencyTransactionType type, Long externalId) {
        return process(currencyName, toPublic, fromPrivate, value, type, externalId, null);
    }

    private CryptoCurrencyTransactionDTO process(CurrencyName currencyName, String toPublic, String fromPrivate, BigInteger value, CryptoCurrencyTransactionType type, Long externalId, Long mainOrderId) {
        final CryptoCurrencyTransactionStatus status = type.equals(CryptoCurrencyTransactionType.ORDER) ?
            CryptoCurrencyTransactionStatus.IN_PROCESS : CryptoCurrencyTransactionStatus.EXECUTED;

        CryptoCurrencyTransactionRepository txRepo = cryptoTransactionRepositoryFactory.getRepository(currencyName, status);
        INode node = nodeAdapterFactory.getNode(currencyName);
        CryptoCurrencyTransaction tx = currencyEntityFactory.makeTx(currencyName, status, null);

        tx.setValue(value);
        tx.setType(type);
        tx.setCreateDate(ZonedDateTime.now());
        tx.setExternalId(externalId);
        tx.setStatus(CryptoCurrencyTransactionStatus.IN_PROCESS);
        tx.setTx(node.transfer(toPublic, fromPrivate, value));
        // only when type == ORDER
        tx.setOrderPairId(mainOrderId);

        tx = txRepo.save(tx);

        return cryptoCurrencyTransactionMapperFactory.getMapper(currencyName, status).toDto(tx);
    }
}
