package com.exc.service;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CryptoCurrencyTransactionType;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.dto.PreparedExDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CryptoCurrencyTransactionServiceTest extends AbstractServiceTest {
    @Autowired
    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;
    @MockBean
    private TxMonitorService txMonitorService;

    @Before
    public void init() throws InterruptedException {
        doNothing().when(txMonitorService).monitorOrderTx(any(), any(), any());
    }

    @Test
    public void checkMigrateAndFetch() {
        //CurrencyName currencyName, String toPublic, String fromPrivate, BigInteger value, CryptoCurrencyTransactionType type, Long externalId) {
        CryptoCurrencyTransactionDTO txDTO = cryptoCurrencyTransactionService.process(buy, "0xas", "0xpriv", BigInteger.TEN, CryptoCurrencyTransactionType.ORDER, 1l);
        assertThat(txDTO.getId()).isNotNull();
        assertThat(cryptoCurrencyTransactionService.findByExternalId(1l, buy, CryptoCurrencyTransactionStatus.IN_PROCESS).size()).isEqualTo(1);
        assertThat(cryptoCurrencyTransactionService.findByExternalId(Arrays.asList(1l), buy, CryptoCurrencyTransactionStatus.IN_PROCESS).size()).isEqualTo(1);
        assertThat(cryptoCurrencyTransactionService.findOne(txDTO.getId(), buy, CryptoCurrencyTransactionStatus.IN_PROCESS)).isNotNull();

        assertThat(cryptoCurrencyTransactionService.findAll(Pageable.unpaged(), buy, CryptoCurrencyTransactionStatus.IN_PROCESS).getTotalElements()).isEqualTo(1);

    }

    @Test
    public void checkOrderTxs() {
        PreparedExDTO exDTO = new PreparedExDTO();
        exDTO.setCurIdBuy(buy);
        exDTO.setCurIdSell(sell);

        exDTO.setExternalIdBuy(1l);
        exDTO.setExternalIdSell(2l);

        exDTO.setValueBuy(BigInteger.TEN);
        exDTO.setValueSell(BigInteger.TEN);
        exDTO.setFromPrivateBuy("0xs");
        exDTO.setFromPrivateSell("0xs");
        exDTO.setToPublicBuy("0xDasd");
        exDTO.setToPublicSell("0xDasd");

        cryptoCurrencyTransactionService.processOrderTransactions(Arrays.asList(exDTO));
        verify(txMonitorService, times(1)).monitorOrderTx(any(), any(), any());
    }

}
