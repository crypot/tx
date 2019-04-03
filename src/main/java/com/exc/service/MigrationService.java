package com.exc.service;

import com.exc.domain.CurrencyName;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.dto.remote.OrderMigrateRequestDTO;
import com.exc.service.node.NodeAdapterFactory;
import com.exc.service.node.NodeTxStatusDTO;
import com.exc.service.remote.OpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * helper class that initiates orders/tx(entity) migration between tables within single DB
 */
@Service
@Transactional
public class MigrationService {
    private final Logger log = LoggerFactory.getLogger(MigrationService.class);
    private final NodeAdapterFactory nodeAdapterFactory;
    private final OpService opService;
    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;

    public MigrationService(NodeAdapterFactory nodeAdapterFactory, OpService opService) {
        this.nodeAdapterFactory = nodeAdapterFactory;
        this.opService = opService;
    }

    @Autowired
    public void setCryptoCurrencyTransactionService(@Lazy CryptoCurrencyTransactionService cryptoCurrencyTransactionService) {
        this.cryptoCurrencyTransactionService = cryptoCurrencyTransactionService;
    }

    public void migrateOrder(OrderMigrateRequestDTO req) {
        Map<Long, Long> ids = opService.migrate(req);
        if (ids.size() == 0) {
            log.warn("Order {}, already migrated ? ", req.getMainOrderId());
            List<CryptoCurrencyTransactionDTO> rs = cryptoCurrencyTransactionService.findActiveByMainOrderId(req.getMainOrderId(), req.getBuy(), req.getSell());
            if (rs.size() > 0) {
                log.error("Failed migrate tx for main order id {}, manual investigation required in TX micorservice!", req.getMainOrderId());
            }

        } else {
            for (Map.Entry<Long, Long> id : ids.entrySet()) {
                cryptoCurrencyTransactionService.migrateTx(req.getBuy(), id.getKey(), id.getValue());
                cryptoCurrencyTransactionService.migrateTx(req.getSell(), id.getKey(), id.getValue());
            }
        }
    }

    public void migrateTx(CurrencyName currencyName, Long txId, NodeTxStatusDTO statusDTO) {
        cryptoCurrencyTransactionService.updateTxStatus(currencyName, txId, statusDTO);
    }


}
