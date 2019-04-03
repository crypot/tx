package com.exc.service;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TxMonitorServiceTest extends AbstractServiceTest {
    @MockBean
    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;
    @MockBean
    private MigrationService migrationService;
    @Autowired
    private TxMonitorService txMonitorService;


    @Before
    public void init() throws InterruptedException {

        doNothing().when(migrationService).migrateOrder(any());
        doNothing().when(cryptoCurrencyTransactionService).updateTxStatus(any(), any(), any());
        when(cryptoCurrencyTransactionService.findOne(any(), any(), any())).thenReturn(tx);
        when(cryptoCurrencyTransactionService.findActiveByMainOrderId(any(), any(), any())).thenReturn(Arrays.asList(tx));

    }

    @Test
    public void checkOrderMonitor() throws InterruptedException {
        txMonitorService.monitorOrderTx(1l, buy, sell);
        synchronized (this) {
            wait(2001);
        }
        verify(migrationService, times(1)).migrateOrder(any());
        verify(cryptoCurrencyTransactionService, times(1)).updateTxStatus(any(), any(), any());

    }

    @Test
    public void checkTxMonitor() throws InterruptedException {
        txMonitorService.monitorTx(1l, buy, CryptoCurrencyTransactionStatus.IN_PROCESS);
        synchronized (this) {
            wait(2001);
        }
        verify(cryptoCurrencyTransactionService, times(1)).updateTxStatus(any(), any(), any());
        verify(migrationService, times(1)).migrateTx(any(), any(), any());

    }


}
