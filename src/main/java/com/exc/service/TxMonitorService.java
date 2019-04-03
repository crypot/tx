package com.exc.service;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CurrencyName;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.dto.remote.OrderMigrateRequestDTO;
import com.exc.service.node.INode;
import com.exc.service.node.NodeAdapterFactory;
import com.exc.service.node.NodeTxStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Responsible for monitoring executed transactions in different nodes, async
 */
@Service
public class TxMonitorService {
    private final Logger log = LoggerFactory.getLogger(TxMonitorService.class);
    private NodeAdapterFactory nodeAdapterFactory;
    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;
    private ScheduledExecutorService scheduler;
    private MigrationService migrationService;

    public TxMonitorService(NodeAdapterFactory nodeAdapterFactory, MigrationService migrationService) {
        this.nodeAdapterFactory = nodeAdapterFactory;
        this.migrationService = migrationService;
    }

    @Autowired
    @Qualifier("txMonitorScheduler")
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    @Autowired
    public void setCryptoCurrencyTransactionService(@Lazy CryptoCurrencyTransactionService cryptoCurrencyTransactionService) {
        this.cryptoCurrencyTransactionService = cryptoCurrencyTransactionService;
    }


    /**
     * Start monitor external transactions in batch
     */

    public void monitorOrderTx(Long mainOrderId, CurrencyName buy, CurrencyName sell) {
        log.info("Starting monitoring order id {}, and pair {}/{}", mainOrderId, buy, sell);
        //make sure it is main order
        INode buyNode = nodeAdapterFactory.getNode(buy);
        INode sellNode = nodeAdapterFactory.getNode(sell);
        //run with delay for specific currency
        scheduler.schedule(() -> {
                try {
                    List<CryptoCurrencyTransactionDTO> txChain = cryptoCurrencyTransactionService.findActiveByMainOrderId(mainOrderId, buy, sell);

                    boolean processed = updateOrderTxStatus(txChain);

                    if (!processed) {
                        log.warn("Recursion, since tx at order {} were not processed!", mainOrderId);
                        monitorOrderTx(mainOrderId, buy, sell);
                    } else {
                        migrationService.migrateOrder(new OrderMigrateRequestDTO(mainOrderId, buy, sell));
                        log.info("after txMonitorAsyncDelegateService.migrateOrder(orderId, pairId, status)");
                    }
                } catch (Exception ex) {
                    log.error("Order {}/{}/{} monitor failed", mainOrderId, buy, sell, ex);
                }
            },
            buyNode.getTxDelay() > sellNode.getTxDelay() ?
                buyNode.getTxDelay() : sellNode.getTxDelay(),
            TimeUnit.SECONDS);


    }


    private boolean updateOrderTxStatus(List<CryptoCurrencyTransactionDTO> txChain) {
        boolean processed = true;
        for (CryptoCurrencyTransactionDTO txDto : txChain) {

            NodeTxStatusDTO statusDTO = nodeAdapterFactory.getNode(txDto.getCurrencyName()).getStatus(txDto.getTx());
            txDto.setStatus(statusDTO.getStatus());
            txDto.setMessage(statusDTO.getMsg());

            if (txDto.getStatus().equals(CryptoCurrencyTransactionStatus.IN_PROCESS)) {
                log.warn("Not processed tx found, cur {}, txId {}", txDto.getCurrencyName(), txDto.getId());
                processed = false;
            }
            cryptoCurrencyTransactionService.updateTxStatus(txDto.getCurrencyName(), txDto.getId(), statusDTO);
        }
        return processed;
    }


    /**
     * Monitor transactions, and update status.
     *
     * @param txId
     * @param currencyName
     */
    public void monitorTx(Long txId, CurrencyName currencyName, CryptoCurrencyTransactionStatus status) {
        log.info("Starting monitoring tx {}, for currency {}", txId, currencyName);

        CryptoCurrencyTransactionDTO tx = cryptoCurrencyTransactionService.findOne(txId, currencyName, status);
        if (tx.getStatus().equals(CryptoCurrencyTransactionStatus.IN_PROCESS)) {

            INode node = nodeAdapterFactory.getNode(currencyName);
            //run with delay for specific currency
            scheduler.schedule(() -> {

                    try {
                        NodeTxStatusDTO statusDTO = node.getStatus(tx.getTx());
                        tx.setStatus(statusDTO.getStatus());
                        tx.setMessage(statusDTO.getMsg());
                        if (statusDTO.getStatus().equals(CryptoCurrencyTransactionStatus.IN_PROCESS)) {
                            log.warn("Recursion, since tx {} were not processed!", tx.getId());
                            monitorTx(txId, currencyName, status);
                        } else {
                            cryptoCurrencyTransactionService.updateTxStatus(currencyName, txId, statusDTO);
                            migrationService.migrateTx(currencyName, txId, statusDTO);
                        }
                    } catch (Exception ex) {
                        log.error("Tx {} cur {} monitor failed", txId, currencyName, ex);
                    }
                },
                node.getTxDelay(),
                TimeUnit.SECONDS);


        } else {
            log.warn("No need to monitor transaction {}, because it is already processed.", txId);
        }

    }


}
